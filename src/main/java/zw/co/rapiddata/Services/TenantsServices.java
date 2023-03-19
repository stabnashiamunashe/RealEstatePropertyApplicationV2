package zw.co.rapiddata.Services;

import jakarta.persistence.EntityNotFoundException;
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

    public Tenant createTenant(Tenant tenant) {
        tenant.setPassword(passwordEncoder.encode(tenant.getPassword()));
        tenant.setRoles("TENANT");
        return tenantsRepository.save(tenant);
    }

    public Tenant updateTenant(Long tenantId, Tenant tenantUpdate){
        Tenant tenant = tenantsRepository.findById(tenantId).orElseThrow(() -> new EntityNotFoundException("Location not found with id " + tenantId));
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

    public Tenant getTenant(Long tenantId) {
        return tenantsRepository.findById(tenantId).orElseThrow(() -> new EntityNotFoundException("Location not found with id " + tenantId));
    }

    public List<Tenant> getAllTenants() {
        return tenantsRepository.findAll();
    }
}
