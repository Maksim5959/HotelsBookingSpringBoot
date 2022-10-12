package com.chuyashkou.hotels_booking.service.impl;

import com.chuyashkou.hotels_booking.model.*;
import com.chuyashkou.hotels_booking.repository.HotelRepository;
import com.chuyashkou.hotels_booking.service.AddressService;
import com.chuyashkou.hotels_booking.service.HotelService;
import com.chuyashkou.hotels_booking.service.UserService;
import com.chuyashkou.hotels_booking.util.SecurityContextHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final UserService userService;
    private final AddressService addressService;

    public HotelServiceImpl(HotelRepository hotelRepository, UserService userService, AddressService addressService) {
        this.hotelRepository = hotelRepository;
        this.userService = userService;
        this.addressService = addressService;
    }

    @Override
    @Transactional
    public Optional<Hotel> save(Hotel hotel) {
        Optional<User> optionalUser = userService.findById(SecurityContextHandler.getUserId());
        AtomicReference<Optional<Hotel>> atomicReference = new AtomicReference<>(Optional.empty());
        optionalUser.ifPresent(user -> {
            if (this.nonDuplicateLegalNameAndAddress(hotel)) {
                user.getRoles().add(Role.MANAGER);
                SecurityContextHandler.updateUserRoles(user);
                hotel.setUser(user);
                atomicReference.set(Optional.of(hotelRepository.save(hotel)));
            }
        });
        return atomicReference.get();
    }

    @Override
    @Transactional
    public Optional<Hotel> update(Hotel hotel) {
        Optional<User> optionalUser = userService.findById(SecurityContextHandler.getUserId());
        AtomicReference<Optional<Hotel>> atomicReference = new AtomicReference<>(Optional.empty());
        optionalUser.ifPresent(user -> {
            if (this.nonDuplicateHotel(hotel)) {
                this.setHotelFields(hotel, user);
                atomicReference.set(Optional.of(hotelRepository.save(hotel)));
            }
        });
        return atomicReference.get();
    }

    @Override
    @Transactional
    public Optional<Hotel> findByUserId(Long userId) {
        return hotelRepository.findByUserId(userId);
    }


    @Override
    @Transactional
    public Optional<Hotel> findById(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        optionalHotel.ifPresent(this::setHotelMinPrice);
        return optionalHotel;
    }

    @Override
    @Transactional
    public Optional<Hotel> delete(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        AtomicReference<Optional<Hotel>> atomicReference = new AtomicReference<>(optionalHotel);
        optionalHotel.ifPresent(hotel -> {
            User user = hotel.getUser();
            if (this.noBookingsForThisHotel(hotel)) {
                user.setRoles(new HashSet<>(List.of(Role.USER)));
                user.setHotel(null);
                userService.save(user);
                hotelRepository.deleteById(id);
                SecurityContextHandler.updateUserRoles(user);
                atomicReference.set(Optional.empty());
            }
        });
        return atomicReference.get();
    }

    @Override
    @Transactional
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> findByHotelSearchData(HotelsSearchData searchData) {
        List<Hotel> hotels = hotelRepository.findAll();
        this.filterHotels(hotels, searchData);
        hotels.forEach(this::setHotelMinPrice);
        return hotels;
    }

    private void setHotelFields(Hotel hotel, User user) {
        hotel.setUser(user);
        hotel.setApartments(user.getHotel().getApartments());
    }

    @Override
    public void updateRegistrationField(Long id, boolean registered) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            hotel.setRegistered(registered);
            hotelRepository.save(hotel);
        }
    }

    @Override
    public void addNewApartment(Apartment apartment) {
        Optional<User> optionalUser = userService.findById(SecurityContextHandler.getUserId());
        optionalUser.ifPresent(user -> {
            Hotel hotel = user.getHotel();
            hotel.getApartments().add(apartment);
            hotelRepository.save(hotel);
        });
    }

    private void filterHotels(List<Hotel> hotels, HotelsSearchData searchData) {
        this.removeBookedApartments(hotels, searchData);
        this.removeInvalidHotels(hotels, searchData);
        this.sortHotels(hotels, searchData.getHotelsFilter());
    }

    private void removeBookedApartments(List<Hotel> hotels, HotelsSearchData searchData) {
        for (Hotel hotel : hotels) {
            hotel.getApartments().removeIf(apartment -> {
                for (Booking booking : apartment.getBookings()) {
                    if (booking.isConfirm() && !(booking.getDateIn().isAfter(searchData.getDateOut())
                            || booking.getDateOut().isBefore(searchData.getDateIn()))) {
                        return true;
                    }
                }
                return false;
            });
        }
    }

    private void removeInvalidHotels(List<Hotel> hotels, HotelsSearchData searchData) {
        hotels.removeIf(hotel -> hotel.getApartments().size() < searchData.getRoomsCount());
        hotels.removeIf(hotel -> {
            Set<Apartment> apartments = hotel.getApartments();
            int adultsSleepingPlaces = 0;
            int childrenSleepingPlaces = 0;
            for (Apartment apartment : apartments) {
                adultsSleepingPlaces += apartment.getDoubleBedCount() * 2 + apartment.getSingleBedCount();
                childrenSleepingPlaces += apartment.getDoubleBedCount() + apartment.getSingleBedCount();
            }
            return adultsSleepingPlaces < searchData.getAdultsCount() || childrenSleepingPlaces < searchData.getChildrenCount();
        });
    }

    private void sortHotels(List<Hotel> hotels, HotelsFilter filter) {
        hotels.sort(filter.getComparator());
    }

    private void setHotelMinPrice(Hotel hotel) {
        hotel.setMinPrice(hotel.getApartments().stream()
                .map(Apartment::getPrice)
                .reduce((p1, p2) -> p1 < p2 ? p1 : p2).orElse(0.0));
    }

    private boolean noBookingsForThisHotel(Hotel hotel) {
        List<Booking> bookings = hotel.getApartments().stream()
                .flatMap(apartment -> apartment.getBookings().stream())
                .collect(Collectors.toList());
        return bookings.size() == 0;
    }

    private boolean nonDuplicateHotel(Hotel hotel) {
        Long hotelIdByLegalName = this.findHotelIdByLegalName(hotel);
        Long hotelIdByAddress = this.findHotelIdByAddress(hotel.getAddress());
        return (hotelIdByLegalName.equals(-1L) || hotelIdByLegalName.equals(hotel.getId())) &&
                (hotelIdByAddress.equals(-1L) || hotelIdByAddress.equals(hotel.getId()));
    }

    private Long findHotelIdByLegalName(Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findByLegalName(hotel.getLegalName());
        if (optionalHotel.isPresent()) {
            return optionalHotel.get().getId();
        } else {
            return -1L;
        }
    }

    private Long findHotelIdByAddress(Address address) {
        Optional<Hotel> optionalHotel;
        Optional<Address> optionalAddress = addressService.findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
                (address.getCountry(), address.getCity(), address.getStreet(),
                        address.getHouse(), address.getBuilding(), address.getApartmentNumber());
        if (optionalAddress.isPresent()) {
            optionalHotel = hotelRepository.findByAddressId(optionalAddress.get().getId());
        } else {
            return -1L;
        }
        if (optionalHotel.isPresent()) {
            return optionalHotel.get().getId();
        } else {
            return -1L;
        }
    }

    private boolean nonDuplicateLegalNameAndAddress(Hotel hotel) {
        Address address = hotel.getAddress();
        Optional<Address> optionalAddress = addressService.findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
                (address.getCountry(), address.getCity(), address.getStreet(),
                        address.getHouse(), address.getBuilding(), address.getApartmentNumber());
        Optional<Hotel> optionalHotel = hotelRepository.findByLegalName(hotel.getLegalName());
        return optionalAddress.isEmpty() && optionalHotel.isEmpty();
    }
}
