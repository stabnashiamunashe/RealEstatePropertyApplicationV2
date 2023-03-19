package zw.co.rapiddata.Controllers;

import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/all_by_price_range")
    public List<Property> getPropertiesByPriceRange(@RequestParam Double minPrice,@RequestParam  Double maxPrice) {
        return propertyServices.getPropertiesByPriceRange(minPrice, maxPrice);
    }

    @PreAuthorize("hasAuthority('SCOPE_OWNER')")
    @PostMapping("/create")
    public Property create(@RequestBody Property property, @RequestParam Long locationId, Principal principal){
        return propertyServices.createProperty(property, locationId, principal);
    }

    @GetMapping("/owner/{id}")
    public List<Property> getPropertiesByOwnerId(@PathVariable Long id){
        return propertyServices.getPropertiesByOwnerId(id);
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


    @GetMapping()
    public List<Property> getAllProperties(){
        return propertyServices.getAllProperties();
    }

    @GetMapping("/get")
    public List<Property> getProperties(){
        return propertyServices.getProperties();
    }

    @GetMapping("/{propertyId}")
    public Property getPropertyById(@PathVariable Long propertyId) {
        return propertyServices.getProperty(propertyId);
    }

    @GetMapping("/visits/{propertyId}")
    public Long getPropertyVisitsById(@PathVariable Long propertyId) {
        return propertyServices.getPropertyVisits(propertyId);
    }

    @GetMapping("/search/bedrooms")
    public List<Property> searchPropertiesByBedrooms(@RequestParam Integer bedrooms) {
        return propertyServices.findByBedrooms(bedrooms);
    }

    @GetMapping("/search/bathrooms")
    public List<Property> searchPropertiesByBathrooms(@RequestParam Integer bathrooms) {
        return propertyServices.findByBathrooms(bathrooms);
    }

    @GetMapping("/search/property_type")
    public List<Property> searchPropertiesByPropertyType(@RequestParam PropertyType propertyType) {
        return propertyServices.getPropertiesByPropertyType(propertyType);
    }

    @GetMapping("/search")
    public List<Property> findProperty(@RequestParam @Nullable Integer bedrooms,
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

}
