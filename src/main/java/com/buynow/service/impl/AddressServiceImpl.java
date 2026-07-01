package com.buynow.service.impl;

import com.buynow.entity.Address;
import com.buynow.entity.User;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.AddressRepository;
import com.buynow.repository.UserRepository;
import com.buynow.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Address addAddress(Long userId, Address address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long addressId, Address address) {

        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setPincode(address.getPincode());

        return addressRepository.save(existingAddress);
    }

    @Override
    public void deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        addressRepository.delete(address);
    }

    @Override
    public Address getAddressById(Long addressId) {

        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> getAddressesByUser(Long userId) {
        return addressRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
    }
}