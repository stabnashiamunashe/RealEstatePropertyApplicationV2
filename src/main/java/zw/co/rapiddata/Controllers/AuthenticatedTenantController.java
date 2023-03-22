package zw.co.rapiddata.Controllers;

import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.Tenant;
import zw.co.rapiddata.Services.TenantsServices;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/tenants")
public class AuthenticatedTenantController {

    private final TenantsServices tenantsServices;

    public AuthenticatedTenantController(TenantsServices tenantsServices) {
        this.tenantsServices = tenantsServices;
    }

    @PutMapping("/update")
    public Tenant updateTenant(@RequestBody Tenant tenant, Principal principal){
        return tenantsServices.updateTenant(principal.getName(), tenant);
    }

    @DeleteMapping("/delete/{tenantId}")
    public String deleteTenant(@PathVariable Long tenantId){
        return  tenantsServices.deleteTenant(tenantId);
    }


    @GetMapping("/{tenantId}")
    public Tenant getTenant(@PathVariable Long tenantId){
        return tenantsServices.getTenant(tenantId);
    }

    @GetMapping("/logged-in-tenant")
    public Tenant getTenant(Principal principal){
        return tenantsServices.getLoggedInTenant(principal.getName());
    }
}
