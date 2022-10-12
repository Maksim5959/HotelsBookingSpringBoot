package com.chuyashkou.hotels_booking.service.impl;

import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.repository.AddressRepository;
import com.chuyashkou.hotels_booking.repository.UserRepository;
import com.chuyashkou.hotels_booking.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void deleteById(Long id) {
        this.addressRepository.deleteById(id);
    }

    @Override
    public Optional<Address> findByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(User::getAddress);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
            (String country, String city, String street, String house, String building, String apartmentNumber) {
        return addressRepository.findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
                (country, city, street, house, building, apartmentNumber);
    }


}
