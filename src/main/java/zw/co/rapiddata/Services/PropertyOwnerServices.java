package zw.co.rapiddata.Services;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Emails.EmailService;
import zw.co.rapiddata.Models.PropertyOwner;
import zw.co.rapiddata.Models.Tenant;
import zw.co.rapiddata.Models.VerificationModels.PendingPropertyOwner;
import zw.co.rapiddata.Models.VerificationModels.VerificationRepository.PendingPropertyOwnerRepository;
import zw.co.rapiddata.Repositories.PropertyOwnerRepository;

import java.util.List;

@Service
public class PropertyOwnerServices {

    private final PropertyOwnerRepository propertyOwnerRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final PendingPropertyOwnerRepository pendingPropertyOwnerRepository;

    public PropertyOwnerServices(PropertyOwnerRepository propertyOwnerRepository, PasswordEncoder passwordEncoder, EmailService emailService, PendingPropertyOwnerRepository pendingPropertyOwnerRepository) {
        this.propertyOwnerRepository = propertyOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.pendingPropertyOwnerRepository = pendingPropertyOwnerRepository;
    }

    public ResponseEntity<?>  registerOwner(PendingPropertyOwner pendingPropertyOwner) throws MessagingException {
        // Check if email is valid
        if (!emailService.isValidEmail(pendingPropertyOwner.getEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("This email is invalid!");
        }
        // Check if email already exists in database

        if (propertyOwnerRepository.existsByEmail(pendingPropertyOwner.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Registered!");
        }

        // Send verification code to email
        int verificationCode =  emailService.generateVerificationCode();
        boolean verificationSent = emailService.sendVerificationCode(pendingPropertyOwner.getEmail(), verificationCode);

        if (verificationSent) {
            // Save user data to pending users table
            pendingPropertyOwner.setVerificationCode(verificationCode);
            pendingPropertyOwnerRepository.save(pendingPropertyOwner);
            return ResponseEntity.status(HttpStatus.OK).body("Verification Code Was Sent To The Email You Provided!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred!");
        }
    }

    public ResponseEntity<?>  verifyOwner(String email, int verificationCode) {
        // Check if verification code is correct
        PendingPropertyOwner pendingPropertyOwner = pendingPropertyOwnerRepository.findByEmail(email);
        if (pendingPropertyOwner != null && pendingPropertyOwner.getVerificationCode() == verificationCode) {
            // Move user data to users table
            Tenant tenant = new Tenant();
            tenant.setFirstname(pendingPropertyOwner.getFirstname());
            tenant.setLastname(pendingPropertyOwner.getLastname());
            tenant.setEmail(pendingPropertyOwner.getEmail());
            tenant.setMobile(pendingPropertyOwner.getMobile());
            tenant.setRoles("OWNER");
            tenant.setNationalId(pendingPropertyOwner.getNationalId());
            tenant.setPassword(passwordEncoder.encode(pendingPropertyOwner.getPassword()));

            // Delete user data from pending users table
            pendingPropertyOwnerRepository.delete(pendingPropertyOwner);

            return ResponseEntity.status(HttpStatus.OK).body("Owner Successfully Registered!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
        }
    }

    public ResponseEntity<?> createPropertyOwner(PropertyOwner propertyOwner) {
        if (propertyOwnerRepository.existsByEmail(propertyOwner.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Registered!");
        }
        propertyOwner.setPassword(passwordEncoder.encode(propertyOwner.getPassword()));
        propertyOwner.setRoles("OWNER");
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyOwnerRepository.save(propertyOwner));
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
