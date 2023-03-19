package zw.co.rapiddata.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Models.Location;
import zw.co.rapiddata.Repositories.LocationRepository;

import java.util.List;

@Service
public class LocationServices {

    private final LocationRepository locationRepository;

    public LocationServices(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Long locationId, Location locationDetails){
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException("Location not found with id " + locationId));
        location.setName(locationDetails.getName());
        location.setDensity(locationDetails.getDensity());
        return locationRepository.save(location);
    }

    public String deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
        return "Location Deleted!";
    }

    public Location getLocation(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException("Location not found with id " + locationId));
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}


