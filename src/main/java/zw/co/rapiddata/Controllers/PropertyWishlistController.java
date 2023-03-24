package zw.co.rapiddata.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.PropertyWishlist;
import zw.co.rapiddata.Services.PropertyWishlistServices;
import zw.co.rapiddata.Services.TenantsServices;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class PropertyWishlistController {

    private final PropertyWishlistServices propertyWishlistServices;

    private final TenantsServices tenantsServices;

    public PropertyWishlistController(PropertyWishlistServices propertyWishlistServices, TenantsServices tenantsServices) {
        this.propertyWishlistServices = propertyWishlistServices;
        this.tenantsServices = tenantsServices;
    }

    @PreAuthorize("hasAuthority('SCOPE_TENANT')")
    @PostMapping("/authenticated")
    public String addPropertyToWishlist(@RequestParam Long propertyId, Principal principal){
        return propertyWishlistServices.addPropertyToWishlist(propertyId, principal);
    }

    @GetMapping("/authenticated/{id}")
    public List<PropertyWishlist> getPropertyWishlistOfTenant(@PathVariable (value = "propertyId") Long id){
        return propertyWishlistServices.getPropertyWishlistOfTenant(id);
    }

    @GetMapping("/user")
    public List<PropertyWishlist> getTenantWishlist(Principal principal){
        var tenant = tenantsServices.getLoggedInTenant(principal.getName());
        return propertyWishlistServices.getPropertyWishlistOfTenant(tenant.getId());
    }
}
