package com.buynow.controller;

import com.buynow.entity.Address;
import com.buynow.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Address> addAddress(
            @PathVariable Long userId,
            @RequestBody Address address) {

        return new ResponseEntity<>(
                addressService.addAddress(userId, address),
                HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable Long addressId,
            @RequestBody Address address) {

        return ResponseEntity.ok(
                addressService.updateAddress(addressId, address));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long addressId) {

        addressService.deleteAddress(addressId);

        return ResponseEntity.ok("Address deleted successfully.");
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(
            @PathVariable Long addressId) {

        return ResponseEntity.ok(
                addressService.getAddressById(addressId));
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {

        return ResponseEntity.ok(
                addressService.getAllAddresses());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                addressService.getAddressesByUser(userId));
    }
}