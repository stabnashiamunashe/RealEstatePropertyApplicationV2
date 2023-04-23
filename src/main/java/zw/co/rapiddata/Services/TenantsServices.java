package zw.co.rapiddata.Services;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Emails.EmailService;
import zw.co.rapiddata.Models.Tenant;
import zw.co.rapiddata.Models.VerificationModels.PendingTenant;
import zw.co.rapiddata.Models.VerificationModels.VerificationRepository.PendingTenantRepository;
import zw.co.rapiddata.Repositories.TenantsRepository;
import zw.co.rapiddata.SMS.TwilioService;

import java.util.List;

@Service
public class TenantsServices {

    private final TenantsRepository tenantsRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final PendingTenantRepository pendingTenantRepository;

    private final TwilioService twilioService;

    public TenantsServices(TenantsRepository tenantsRepository, PasswordEncoder passwordEncoder, EmailService emailService, PendingTenantRepository pendingTenantRepository, TwilioService twilioService) {
        this.tenantsRepository = tenantsRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.pendingTenantRepository = pendingTenantRepository;
        this.twilioService = twilioService;
    }

    public ResponseEntity<?>  registerTenantByEmail(PendingTenant pendingTenant) throws MessagingException {
        // Check if email is valid
        if (!emailService.isValidEmail(pendingTenant.getEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("This email is invalid!");
        }
        // Check if email already exists in database

        if (tenantsRepository.existsByEmail(pendingTenant.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Registered!");
        }

        if(pendingTenantRepository.existsByEmail(pendingTenant.getEmail())){
            pendingTenantRepository.deleteByEmail(pendingTenant.getEmail());
        }

        int verificationCode = emailService.generateVerificationCode();
        boolean verificationSent = emailService.sendVerificationCode(pendingTenant.getEmail(), verificationCode);

        if (verificationSent) {
            // Save user data to pending users table
            pendingTenant.setVerificationCode(verificationCode);
            pendingTenant.setPassword(passwordEncoder.encode(pendingTenant.getPassword()));
            pendingTenantRepository.save(pendingTenant);
            return ResponseEntity.status(HttpStatus.OK).body("Email Successfully Registered!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
        }
    }

    public ResponseEntity<?>  registerTenantByPhoneNumber(PendingTenant pendingTenant) throws Exception {

        // Check if email is valid
        if (!emailService.isValidEmail(pendingTenant.getEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("This email is invalid!");
        }
        // Check if email already exists in database

        if (tenantsRepository.existsByEmail(pendingTenant.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Registered!");
        }

        if(pendingTenantRepository.existsByEmail(pendingTenant.getEmail())){
            pendingTenantRepository.deleteByEmail(pendingTenant.getEmail());
        }

        // Send verification code to phone number
        int verificationCode =  twilioService.generateVerificationCode();
        boolean verificationSent = twilioService.sendSmsVerificationCode(pendingTenant.getMobile(), verificationCode);

        if (verificationSent) {
            // Save user data to pending users table
            pendingTenant.setVerificationCode(verificationCode);
            pendingTenant.setPassword(passwordEncoder.encode(pendingTenant.getPassword()));
            pendingTenantRepository.save(pendingTenant);
            return ResponseEntity.status(HttpStatus.OK).body("Tenant Successfully Registered!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
        }
    }

    public ResponseEntity<?>  verifyTenant(String email, int verificationCode) {
        // Check if verification code is correct
        PendingTenant pendingTenant = pendingTenantRepository.findByEmail(email);

        if (pendingTenant != null && pendingTenant.getVerificationCode() == verificationCode) {
            // Move user data to users table
            Tenant tenant = new Tenant();
            tenant.setFirstname(pendingTenant.getFirstname());
            tenant.setLastname(pendingTenant.getLastname());
            tenant.setEmail(pendingTenant.getEmail());
            tenant.setMobile(pendingTenant.getMobile());
            tenant.setRoles("TENANT");
            tenant.setNationalId(pendingTenant.getNationalId());
            tenant.setPassword(pendingTenant.getPassword());
            tenantsRepository.save(tenant);
            // Delete user data from pending users table
            pendingTenantRepository.delete(pendingTenant);

            return ResponseEntity.status(HttpStatus.OK).body("Tenant Successfully Registered!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect Code!");
        }
    }

    public Tenant updateTenant(String email , Tenant tenantUpdate){
        Tenant tenant = tenantsRepository.findByEmail(email);
        tenant.setFirstname(tenantUpdate.getFirstname());
        tenant.setLastname(tenantUpdate.getLastname());
        tenant.setEmail(tenantUpdate.getEmail());
        tenant.setMobile(tenantUpdate.getMobile());
        tenant.setPassword(passwordEncoder.encode(tenantUpdate.getPassword()));
        return tenantsRepository.save(tenant);
    }

    public String deleteTenant(Long tenantId) {
        tenantsRepository.deleteById(tenantId);
        return "Property Owner Deleted!";
    }

    public Tenant getLoggedInTenant(String email) {
        return tenantsRepository.findByEmail(email);
    }

    public Tenant getTenant(Long tenantId) {
        return tenantsRepository.findById(tenantId).orElseThrow(() -> new EntityNotFoundException("Tenant not found with id " + tenantId));
    }

    public List<Tenant> getAllTenants() {
        return tenantsRepository.findAll();
    }
}
