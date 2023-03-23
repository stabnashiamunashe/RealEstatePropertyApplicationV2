package zw.co.rapiddata.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.rapiddata.Models.PropertyOwner;
import zw.co.rapiddata.Services.PropertyOwnerServices;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth/property-owner")
public class AuthenticatedOwnerController {

    private final PropertyOwnerServices propertyOwnerServices;

    public AuthenticatedOwnerController(PropertyOwnerServices propertyOwnerServices) {
        this.propertyOwnerServices = propertyOwnerServices;
    }

    @GetMapping()
    public PropertyOwner getLoggedInOwnerDetails(Principal principal){
        return propertyOwnerServices.getPropertyOwnerByEmail(principal.getName());
    }
}
