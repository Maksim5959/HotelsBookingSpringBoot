package com.chuyashkou.hotels_booking.service.impl;

import com.chuyashkou.hotels_booking.model.*;
import com.chuyashkou.hotels_booking.repository.UserRepository;
import com.chuyashkou.hotels_booking.service.AddressService;
import com.chuyashkou.hotels_booking.service.UserService;
import com.chuyashkou.hotels_booking.util.SecurityContextHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AddressService addressService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> save(User user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> userByPhoneNumber = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (userByEmail.isEmpty() && userByPhoneNumber.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            return Optional.of(userRepository.save(user));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> update(User user) {
        Optional<User> optionalUser = userRepository.findById(SecurityContextHandler.getUserId());
        AtomicReference<Optional<User>> atomicReference = new AtomicReference<>(Optional.empty());
        optionalUser.ifPresent(oldUser -> {
            if (this.nonDuplicateUser(user, oldUser)) {
                this.setUserFields(user, oldUser);
                User newUser = userRepository.save(user);
                SecurityContextHandler.updateUserDetails(newUser);
                atomicReference.set(Optional.of(newUser));
            }
        });
        return atomicReference.get();
    }

    @Override
    public Optional<User> deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        AtomicReference<Optional<User>> atomicReference = new AtomicReference<>(optionalUser);
        optionalUser.ifPresent(user -> {
            if (user.getBookings().size() == 0 && !this.isActiveBookingsInHotel(user)) {
                userRepository.deleteById(id);
                if (this.getCountUsersWithAddress(user.getAddress()) == 0) {
                    addressService.deleteById(user.getAddress().getId());
                }
                atomicReference.set(Optional.empty());
            }
        });
        return atomicReference.get();
    }

    @Override
    public List<User> findAllWithoutAdmins() {
        return userRepository.findAll().stream()
                .filter(user -> !user.getRoles().contains(Role.ADMIN))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserAddress(Long addressId) {
        Optional<User> optionalUser = userRepository.findById(SecurityContextHandler.getUserId());
        optionalUser.ifPresent(user -> {
            user.setAddress(null);
            userRepository.save(user);
            if (this.getCountUsersWithAddress(new Address(addressId)) == 0) addressService.deleteById(addressId);
        });
    }

    @Override
    public void saveUserAddress(Address address) {
        Long addressId = this.getAddressId(address);
        Optional<User> optionalUser = userRepository.findById(SecurityContextHandler.getUserId());
        optionalUser.ifPresent(user -> {
            Optional<Address> oldAddress = Optional.ofNullable(user.getAddress());
            if (addressId != -1) address.setId(addressId);
            user.setAddress(address);
            userRepository.save(user);
            if (oldAddress.isPresent() && this.getCountUsersWithAddress(oldAddress.get()) == 0) {
                addressService.deleteById(oldAddress.get().getId());
            }
        });
    }

    @Override
    public void updateUserPassword(String password) {
        Optional<User> optionalUser = userRepository.findById(SecurityContextHandler.getUserId());
        optionalUser.ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        });

    }

    @Override
    public void updateUserActiveField(Long id, boolean isActive) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(user -> {
            user.setActive(isActive);
            userRepository.save(user);
        });
    }

    private void setUserFields(User user, User oldUser) {
        user.setPassword(oldUser.getPassword());
        user.setBookings(oldUser.getBookings());
        if (Objects.nonNull(oldUser.getAddress())) user.setAddress(oldUser.getAddress());
        if (Objects.nonNull(oldUser.getHotel())) user.setHotel(oldUser.getHotel());
    }

    private boolean isActiveBookingsInHotel(User user) {
        if (Objects.nonNull(user.getHotel())) {
            Hotel hotel = user.getHotel();
            List<Booking> bookings = hotel.getApartments().stream()
                    .flatMap(apartment -> apartment.getBookings().stream()
                            .filter(Booking::isConfirm))
                    .collect(Collectors.toList());
            return bookings.size() > 0;
        }
        return false;
    }

    private int getCountUsersWithAddress(Address address) {
        if (address == null) return -1;
        List<User> users = userRepository.findByAddressId(address.getId());
        return users.size();
    }

    private Long getAddressId(Address address) {
        Optional<Address> optionalAddress = addressService.findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
                (address.getCountry(), address.getCity(), address.getStreet(),
                        address.getHouse(), address.getBuilding(), address.getApartmentNumber());
        if (optionalAddress.isPresent()) {
            return optionalAddress.get().getId();
        } else {
            return -1L;
        }
    }

    private boolean nonDuplicateUser(User newUser, User oldUser) {
        Optional<User> optionalUserByEmail = userRepository.findByEmail(newUser.getEmail());
        Optional<User> optionalUserByPhoneNumber = userRepository.findByPhoneNumber(newUser.getPhoneNumber());
        return optionalUserByEmail.map(user -> user.getEmail().equals(oldUser.getEmail())).orElse(true) &&
                optionalUserByPhoneNumber.map(user -> user.getPhoneNumber().equals(oldUser.getPhoneNumber())).orElse(true);
    }
}
