package zw.co.rapiddata.Controllers;

import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.Tenant;
import zw.co.rapiddata.Services.TenantsServices;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantsController {

    private final TenantsServices tenantsServices;

    public TenantsController(TenantsServices tenantsServices) {
        this.tenantsServices = tenantsServices;
    }

    @GetMapping
    public List<Tenant> getAllTenants(){
        return tenantsServices.getAllTenants();
    }

}
