package zw.co.rapiddata.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.PropertyOwner;
import zw.co.rapiddata.Models.Tenant;
import zw.co.rapiddata.Services.TenantsServices;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantsController {

    @Autowired
    private TenantsServices tenantsServices;

    @PutMapping("/update")
    public Tenant updateTenant(@RequestParam Long tenantId,
                               @RequestBody Tenant tenant){

        return tenantsServices.updateTenant(tenantId, tenant);
    }

    @GetMapping
    public List<Tenant> getAllTenants(){
        return tenantsServices.getAllTenants();
    }

    @DeleteMapping("/delete/{tenantId}")
    public String deleteTenant(@PathVariable Long tenantId){
        return  tenantsServices.deleteTenant(tenantId);
    }

    @GetMapping("/{tenantId}")
    public Tenant getTenant(@PathVariable Long tenantId){
        return tenantsServices.getTenant(tenantId);
    }

}
