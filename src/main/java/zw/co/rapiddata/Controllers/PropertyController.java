package zw.co.rapiddata.Controllers;

import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.DTOs.PropertyDTO;
import zw.co.rapiddata.Models.*;
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

    @GetMapping("/owner/{id}")
    public List<PropertyDTO> getPropertiesByOwnerId(@PathVariable Long id){
        return propertyServices.getPropertiesByOwnerId(id);
    }

    @GetMapping()
    public List<PropertyDTO> getAllProperties(){
        return propertyServices.getAllProperties();
    }

    @GetMapping("/property-types")
    public PropertyType[] getAllPropertyTypes() {
        return PropertyType.values();
    }

    @GetMapping("/property-conditions")
    public Conditions[] getAllPropertyConditions(){
        return Conditions.values();
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
                                          @RequestParam @Nullable String location,
                                          @RequestParam @Nullable City city
                                      ){

        return propertyServices.findPropertyByCriteriaSearch(
                bedrooms,
                bathrooms,
                minPrice,
                maxPrice,
                propertyType,
                density,
                location,
                city
                );
    }

}
