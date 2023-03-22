package zw.co.rapiddata.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Models.PropertyOwner;
import zw.co.rapiddata.Repositories.PropertyOwnerRepository;

import java.util.List;

@Service
public class PropertyOwnerServices {

    private final PropertyOwnerRepository propertyOwnerRepository;

    private final PasswordEncoder passwordEncoder;

    public PropertyOwnerServices(PropertyOwnerRepository propertyOwnerRepository, PasswordEncoder passwordEncoder) {
        this.propertyOwnerRepository = propertyOwnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> createPropertyOwner(PropertyOwner propertyOwner) {
        if (propertyOwnerRepository.existsByEmail(propertyOwner.getEmail())){
            propertyOwner.setPassword(passwordEncoder.encode(propertyOwner.getPassword()));
            propertyOwner.setRoles("OWNER");
            return ResponseEntity.status(HttpStatus.CREATED).body(propertyOwnerRepository.save(propertyOwner));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Registered!");
    }

    public PropertyOwner updatePropertyOwner(String email, PropertyOwner propertyOwnerUpdate){
        PropertyOwner propertyOwner = propertyOwnerRepository.findByEmail(email);
        propertyOwner.setFirstname(propertyOwnerUpdate.getFirstname());
        propertyOwner.setLastname(propertyOwnerUpdate.getLastname());
        propertyOwner.setEmail(propertyOwnerUpdate.getEmail());
        propertyOwner.setMobile(propertyOwnerUpdate.getMobile());
        propertyOwner.setPassword(propertyOwnerUpdate.getPassword());
        return propertyOwnerRepository.save(propertyOwner);
    }

    public String deletePropertyOwner(Long propertyOwnerId) {
        propertyOwnerRepository.deleteById(propertyOwnerId);
        return "Property Owner Deleted!";
    }

    public PropertyOwner getPropertyOwner(Long propertyOwnerId) {
        return propertyOwnerRepository.findById(propertyOwnerId).orElseThrow(() -> new EntityNotFoundException("Location not found with id " + propertyOwnerId));
    }

    public List<PropertyOwner> getAllPropertyOwners() {
        return propertyOwnerRepository.findAll();
    }

    public PropertyOwner getPropertyOwnerByEmail(String email) {
        return propertyOwnerRepository.findByEmail(email);
    }
}
