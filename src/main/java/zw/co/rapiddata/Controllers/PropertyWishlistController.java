package zw.co.rapiddata.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.PropertyWishlist;
import zw.co.rapiddata.Services.PropertyWishlistServices;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class PropertyWishlistController {

    private final PropertyWishlistServices propertyWishlistServices;

    public PropertyWishlistController(PropertyWishlistServices propertyWishlistServices) {
        this.propertyWishlistServices = propertyWishlistServices;
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
}
