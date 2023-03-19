package zw.co.rapiddata.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Models.*;
import zw.co.rapiddata.Repositories.LocationRepository;
import zw.co.rapiddata.Repositories.PropertyRepository;

import java.security.Principal;
import java.util.List;

@Service
public class PropertyServices {

    private final PropertyRepository propertyRepository;

    private final LocationRepository locationRepository;

    private final PropertyOwnerServices propertyOwnerServices;

    public PropertyServices(PropertyRepository propertyRepository, LocationRepository locationRepository, PropertyOwnerServices propertyOwnerServices) {
        this.propertyRepository = propertyRepository;
        this.locationRepository = locationRepository;
        this.propertyOwnerServices = propertyOwnerServices;
    }

    public Property FindPropertyById(Long propertyId) {
        return propertyRepository.findById(propertyId).orElse(null);
    }

    public Property createProperty(Property property, Long locationId, Principal principal) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException("Location not found with id " + locationId));
        property.setPropertyOwner(propertyOwnerServices.getPropertyOwnerByEmail(principal.getName()));
        property.setLocation(location);
        property.setVisits(0L);
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long propertyId, Property propertyDetails) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property not found with id " + propertyId));
        property.setDescription(propertyDetails.getDescription());
        property.setPropertyType(propertyDetails.getPropertyType());
        property.setBathrooms(property.getBathrooms());
        property.setPropertyCondition(propertyDetails.getPropertyCondition());
        property.setPrice(propertyDetails.getPrice());
        property.setLocation(propertyDetails.getLocation());
        return propertyRepository.save(property);
    }

    public List<Property> getPropertiesByPriceRange(Double minPrice, Double maxPrice) {
        return propertyRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @PreAuthorize("hasAuthority('SCOPE_OWNER')")
    public String deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
        return "Property Deleted!";
    }

    public Property getProperty(Long propertyId) {
        var property = propertyRepository.findById(propertyId).orElse(null);
        assert property != null;
        property.setVisits(property.getVisits() + 1);
        propertyRepository.save(property);
        return propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property not found with id " + propertyId));
    }

    public Long getPropertyVisits(Long propertyId){
        var property = propertyRepository.findById(propertyId).orElse(null);
        assert property != null;
        return property.getVisits() + 1;
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public List<Property> getPropertiesByLocation(Long locationId) {
        return propertyRepository.findAllByLocationId(locationId);
    }

    public List<Property> getPropertiesByPropertyType(PropertyType propertyType) {
        return propertyRepository.findByPropertyType(propertyType);
    }

    public List<Property> findByBedrooms(Integer bedrooms) {
        return propertyRepository.findByBedrooms(bedrooms);
    }

    public List<Property> findByBathrooms(Integer bathrooms) {
        return propertyRepository.findByBathrooms(bathrooms);
    }

    public List<Property> findPropertyByCriteriaSearch(@Nullable Integer bedrooms, @Nullable Integer bathrooms, @Nullable Double minPrice,@Nullable Double maxPrice, @Nullable PropertyType propertyType, @Nullable Density density, @Nullable String location){
        return propertyRepository.findBySearchCriteria(bedrooms, bathrooms, propertyType, minPrice, maxPrice, density, location);
    }

    public List<Property> getProperties() {
        Page<Property> properties = propertyRepository.findAll(PageRequest.of(0, 50));
        return properties.getContent();
    }

    public List<Property> getPropertiesByOwnerId(Long id) {
        return propertyRepository.findByPropertyOwner_Id(id);
    }
}
