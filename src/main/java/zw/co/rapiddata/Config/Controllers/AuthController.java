package zw.co.rapiddata.Config.Controllers;

import zw.co.rapiddata.Config.Service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.rapiddata.Models.PropertyOwner;
import zw.co.rapiddata.Models.Tenant;
import zw.co.rapiddata.Services.PropertyOwnerServices;
import zw.co.rapiddata.Services.TenantsServices;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final TenantsServices tenantsServices;


    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, PropertyOwnerServices propertyOwnerServices, TenantsServices tenantsServices) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.propertyOwnerServices = propertyOwnerServices;
        this.tenantsServices = tenantsServices;
    }

    private final PropertyOwnerServices propertyOwnerServices;

    @PostMapping("/register/owner")
    public PropertyOwner createOwnerOrAgent(@RequestBody PropertyOwner propertyOwner){
        return propertyOwnerServices.createPropertyOwner(propertyOwner);
    }

    @PostMapping("/register/tenant")
    public Tenant createTenant(@RequestBody Tenant tenant){
        return tenantsServices.createTenant(tenant);
    }

    @PostMapping("/login")
    public String token(@RequestBody LoginRequest loginRequest){

        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
            System.out.println(authentication.getAuthorities());
            return "token : " + tokenService.generateToken(authentication) + "\n role : " + authentication.getAuthorities();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "User Not Found";
        }
    }
}
