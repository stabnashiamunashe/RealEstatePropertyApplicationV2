package zw.co.rapiddata.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Models.Location;
import zw.co.rapiddata.Services.LocationServices;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private final LocationServices locationServices;

    public LocationController(LocationServices locationServices) {
        this.locationServices = locationServices;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/create")
    public Location createLocation(@RequestBody Location location){
        return locationServices.createLocation(location);
    }

    @GetMapping("/all")
    public List<Location> getAllLocations(){
        return locationServices.getAllLocations();
    }

    @GetMapping("/location")
    public Location getLocationById(@RequestParam Long locationId){
        return locationServices.getLocation(locationId);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/update/{locationId}")
    public Location updateLocation(@RequestBody Location location, @PathVariable Long locationId){
        return locationServices.updateLocation(locationId, location);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/delete/{locationId}")
    public String deleteLocation(@PathVariable Long locationId){
        return locationServices.deleteLocation(locationId);
    }
}
