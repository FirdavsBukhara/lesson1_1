package uz.pdp.lesson1_1.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson1_1.entity.Address;
import uz.pdp.lesson1_1.payload.AddressDto;
import uz.pdp.lesson1_1.payload.ApiResponse;
import uz.pdp.lesson1_1.service.AddressService;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/api/address")
    public ResponseEntity<List<Address>> getAddreses() {
        List<Address> addreses = addressService.getAddreses();
        return ResponseEntity.ok(addreses);
    }

    @GetMapping("/api/address/{id}")
    public ResponseEntity<Address> getAdress(@PathVariable Integer id) {
        Address addressById = addressService.getAddressById(id);
        return ResponseEntity.ok(addressById);
    }

    @PostMapping("/api/address")
    public ResponseEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/api/address/{id}")
    public ResponseEntity<ApiResponse> editAddress(@PathVariable Integer id,@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);

        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/api/address/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id){
        ApiResponse apiResponse = addressService.deleteAddress(id);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
}
