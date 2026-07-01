package com.buynow.service;

import com.buynow.entity.Address;

import java.util.List;

public interface AddressService {
    Address addAddress(Long userId, Address address);

    Address updateAddress(Long addressId, Address address);

    void deleteAddress(Long addressId);

    Address getAddressById(Long addressId);

    List<Address> getAllAddresses();

    List<Address> getAddressesByUser(Long userId);

}
