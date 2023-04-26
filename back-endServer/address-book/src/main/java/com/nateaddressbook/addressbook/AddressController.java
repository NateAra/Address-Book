package com.nateaddressbook.addressbook;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address){
        return addressRepository.save(address);
    }

    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable Long id){
        return addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Long id, @RequestBody Address address){
        Address existingAddress = addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
        existingAddress.setName(address.getName());
        existingAddress.setCity(address.getCity());
        existingAddress.setStreet(address.getStreet());
        return addressRepository.save(existingAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id){
        Address address = addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
        addressRepository.delete(address);
        return ResponseEntity.ok().build();
    }
    @Configuration
    public static class WebConfig implements WebMvcConfigurer{
        @Override
        public void addCorsMappings(CorsRegistry registry){
            registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
        }
    }

}
