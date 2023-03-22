package zw.co.rapiddata.Controllers;

import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.Density;
import zw.co.rapiddata.Models.Property;
import zw.co.rapiddata.Models.PropertyType;
import zw.co.rapiddata.Services.PropertyServices;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/properties")
public class AuthenticatedPropertyController {

    private final PropertyServices propertyServices;

    public AuthenticatedPropertyController(PropertyServices propertyServices) {
        this.propertyServices = propertyServices;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping()
    public List<Property> getAllPropertiesWithToken(){
        return propertyServices.getAllPropertiesWithToken();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping("/{propertyId}")
    public Property getPropertyByIdWithAuth(@PathVariable Long propertyId) {
        return propertyServices.getPropertyWithToken(propertyId);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping("/visits/{propertyId}")
    public Long getPropertyVisitsByIdWithAuth(@PathVariable Long propertyId) {
        return propertyServices.getPropertyVisits(propertyId);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping("/search")
    public List<Property> findPropertyWithAuth(@RequestParam @Nullable Integer bedrooms,
                                               @RequestParam @Nullable Integer bathrooms,
                                               @RequestParam @Nullable Double minPrice,
                                               @RequestParam @Nullable Double maxPrice,
                                               @RequestParam @Nullable PropertyType propertyType,
                                               @RequestParam @Nullable Density density,
                                               @RequestParam @Nullable String location
    ){

        return propertyServices.findPropertyByCriteriaSearchWithToken(
                bedrooms,
                bathrooms,
                minPrice,
                maxPrice,
                propertyType,
                density,
                location
        );
    }

    @PreAuthorize("hasAuthority('SCOPE_OWNER')")
    @PostMapping("/create")
    public Property create(@RequestBody Property property, @RequestParam Long locationId, Principal principal){
        return propertyServices.createProperty(property, locationId, principal);
    }

    @PreAuthorize("hasAuthority('SCOPE_OWNER')")
    @PutMapping("/update/{propertyId}")
    public Property updateLocation(@RequestBody Property property, @PathVariable Long propertyId){
        return propertyServices.updateProperty(propertyId, property);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/update/propertySubDetails")
    public Property updatePropertySubscriptionDetails(){
        return null;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_ADMIN')")
    @DeleteMapping("/delete/{propertyId}")
    public String deleteProperty(@PathVariable Long propertyId){
        return propertyServices.deleteProperty(propertyId);
    }

}
