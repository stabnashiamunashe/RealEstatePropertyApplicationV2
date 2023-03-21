package zw.co.rapiddata.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.Tenant;
import zw.co.rapiddata.Services.TenantsServices;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantsController {

    @Autowired
    private TenantsServices tenantsServices;

    @PutMapping("/authenticated/update")
    public Tenant updateTenant(@RequestParam Long tenantId,
                               @RequestBody Tenant tenant){

        return tenantsServices.updateTenant(tenantId, tenant);
    }

    @GetMapping
    public List<Tenant> getAllTenants(){
        return tenantsServices.getAllTenants();
    }

    @DeleteMapping("/authenticateddelete/{tenantId}")
    public String deleteTenant(@PathVariable Long tenantId){
        return  tenantsServices.deleteTenant(tenantId);
    }


    @GetMapping("/authenticated/{tenantId}")
    public Tenant getTenant(@PathVariable Long tenantId){
        return tenantsServices.getTenant(tenantId);
    }

    @GetMapping("/authenticated/logged-in-tenant")
    public Tenant getTenant(Principal principal){
        return tenantsServices.getLoggedInTenant(principal.getName());
    }

}
