package uz.pdp.lesson1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.lesson1_1.entity.Address;
import uz.pdp.lesson1_1.payload.AddressDto;
import uz.pdp.lesson1_1.payload.ApiResponse;
import uz.pdp.lesson1_1.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;


    public List<Address> getAddreses() {
        List<Address> addresList = addressRepository.findAll();
        return addresList;
    }

    public Address getAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            return optionalAddress.get();
        }
        return null;
    }

    public ApiResponse addAddress(AddressDto addressDto) {

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address is Succesfully added", true);
    }

    public ApiResponse editAddress(Integer id,AddressDto addressDto){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()){
            return new ApiResponse("Such address doesn't exsist",false);
        }
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address changed",true);
    }

    public ApiResponse deleteAddress(Integer id){
        addressRepository.deleteById(id);
        return new ApiResponse("Address deleted",true);
    }
}
