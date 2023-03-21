package zw.co.rapiddata.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.PropertyOwner;
import zw.co.rapiddata.Services.PropertyOwnerServices;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property_owners")
public class PropertyOwnerController {

    private final PropertyOwnerServices propertyOwnerServices;

    public PropertyOwnerController(PropertyOwnerServices propertyOwnerServices) {
        this.propertyOwnerServices = propertyOwnerServices;
    }

    @PreAuthorize("hasAuthority('SCOPE_OWNER')")
    @PutMapping("/authenticated/update")
    public PropertyOwner updatePropertyOwner(@RequestParam Long propertyOwnerId,
                                             @RequestBody PropertyOwner propertyOwner){

        return propertyOwnerServices.updatePropertyOwner(propertyOwnerId, propertyOwner);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/authenticated")
    public List<PropertyOwner> getAllPropertyOwners(){
        return propertyOwnerServices.getAllPropertyOwners();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/authenticated/delete/{propertyOwnerId}")
    public String deletePropertyOwner(@PathVariable Long propertyOwnerId){
        return  propertyOwnerServices.deletePropertyOwner(propertyOwnerId);
    }

    @PreAuthorize("hasAnyAuthority()('SCOPE_OWNER','SCOPE_TENANT','SCOPE_ADMIN')")
    @GetMapping("/authenticated/{propertyOwnerId}")
    public PropertyOwner getPropertyOwner(@PathVariable Long propertyOwnerId){
        return propertyOwnerServices.getPropertyOwner(propertyOwnerId);
    }

}
