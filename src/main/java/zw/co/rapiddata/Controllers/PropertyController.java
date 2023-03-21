package zw.co.rapiddata.Controllers;

import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.DTOs.PropertyDTO;
import zw.co.rapiddata.Models.Density;
import zw.co.rapiddata.Models.Property;
import zw.co.rapiddata.Models.PropertyType;
import zw.co.rapiddata.Services.PropertyServices;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final PropertyServices propertyServices;

    public PropertyController(PropertyServices propertyServices) {
        this.propertyServices = propertyServices;
    }

    @PreAuthorize("hasAuthority('SCOPE_OWNER')")
    @PostMapping("/authenticated/create")
    public Property create(@RequestBody Property property, @RequestParam Long locationId, Principal principal){
        return propertyServices.createProperty(property, locationId, principal);
    }

    @GetMapping("/owner/{id}")
    public List<PropertyDTO> getPropertiesByOwnerId(@PathVariable Long id){
        return propertyServices.getPropertiesByOwnerId(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_OWNER')")
    @PutMapping("/authenticated/update/{propertyId}")
    public Property updateLocation(@RequestBody Property property, @PathVariable Long propertyId){
        return propertyServices.updateProperty(propertyId, property);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/authenticated/update/propertySubDetails")
    public Property updatePropertySubscriptionDetails(){
        return null;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_ADMIN')")
    @DeleteMapping("/authenticated/delete/{propertyId}")
    public String deleteProperty(@PathVariable Long propertyId){
        return propertyServices.deleteProperty(propertyId);
    }


    @GetMapping()
    public List<PropertyDTO> getAllProperties(){
        return propertyServices.getAllProperties();
    }

    @GetMapping("/{propertyId}")
    public PropertyDTO getPropertyById(@PathVariable Long propertyId) {
        return propertyServices.getProperty(propertyId);
    }

    @GetMapping("/search")
    public List<PropertyDTO> findProperty(@RequestParam @Nullable Integer bedrooms,
                                       @RequestParam @Nullable Integer bathrooms,
                                       @RequestParam @Nullable Double minPrice,
                                       @RequestParam @Nullable Double maxPrice,
                                       @RequestParam @Nullable PropertyType propertyType,
                                       @RequestParam @Nullable Density density,
                                       @RequestParam @Nullable String location
                                      ){

        return propertyServices.findPropertyByCriteriaSearch(
                bedrooms,
                bathrooms,
                minPrice,
                maxPrice,
                propertyType,
                density,
                location
                );
    }

    /// WITH TOKENS

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping("/authenticated/all")
    public List<Property> getAllPropertiesWithToken(){
        return propertyServices.getAllPropertiesWithToken();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping("/authenticated/{propertyId}")
    public Property getPropertyByIdWithAuth(@PathVariable Long propertyId) {
        return propertyServices.getPropertyWithToken(propertyId);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping("/authenticated/visits/{propertyId}")
    public Long getPropertyVisitsByIdWithAuth(@PathVariable Long propertyId) {
        return propertyServices.getPropertyVisits(propertyId);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping("/authenticated/search")
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

}
