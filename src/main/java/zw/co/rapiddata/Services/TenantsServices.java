package zw.co.rapiddata.Services;

import com.azure.core.http.rest.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Models.Tenant;
import zw.co.rapiddata.Repositories.TenantsRepository;

import java.util.List;

@Service
public class TenantsServices {

    private final TenantsRepository tenantsRepository;

    private final PasswordEncoder passwordEncoder;

    public TenantsServices(TenantsRepository tenantsRepository, PasswordEncoder passwordEncoder) {
        this.tenantsRepository = tenantsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> createTenant(Tenant tenant) {
        if (tenantsRepository.existsByEmail(tenant.getEmail())){
            tenant.setPassword(passwordEncoder.encode(tenant.getPassword()));
            tenant.setRoles("TENANT");
            return ResponseEntity.status(HttpStatus.CREATED).body(tenantsRepository.save(tenant));
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Registered!");

    }

    public Tenant updateTenant(String email , Tenant tenantUpdate){
        Tenant tenant = tenantsRepository.findByEmail(email);
        tenant.setFirstname(tenantUpdate.getFirstname());
        tenant.setLastname(tenantUpdate.getLastname());
        tenant.setEmail(tenantUpdate.getEmail());
        tenant.setMobile(tenantUpdate.getMobile());
        tenant.setPassword(tenantUpdate.getPassword());
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
