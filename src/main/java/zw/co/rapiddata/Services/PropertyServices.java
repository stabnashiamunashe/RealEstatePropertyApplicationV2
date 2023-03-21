package zw.co.rapiddata.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.DTOs.CommentsDTO;
import zw.co.rapiddata.DTOs.PropertyDTO;
import zw.co.rapiddata.DTOs.PropertyOwnerDTO;
import zw.co.rapiddata.Models.*;
import zw.co.rapiddata.Repositories.CommentsRepository;
import zw.co.rapiddata.Repositories.LocationRepository;
import zw.co.rapiddata.Repositories.PropertyOwnerRepository;
import zw.co.rapiddata.Repositories.PropertyRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServices {

    private final PropertyRepository propertyRepository;

    private final LocationRepository locationRepository;

    private final PropertyOwnerServices propertyOwnerServices;

    private final PropertyOwnerRepository propertyOwnerRepository;

    private final CommentsRepository commentsRepository;

    public PropertyServices(PropertyRepository propertyRepository, LocationRepository locationRepository, PropertyOwnerServices propertyOwnerServices, CommentsRepository commentsRepository, PropertyOwnerRepository propertyOwnerRepository) {
        this.propertyRepository = propertyRepository;
        this.locationRepository = locationRepository;
        this.propertyOwnerServices = propertyOwnerServices;
        this.commentsRepository = commentsRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
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

    public String deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
        return "Property Deleted!";
    }

    public PropertyDTO getProperty(Long propertyId) {
        var oldProperty = propertyRepository.findById(propertyId).orElse(null);
        assert oldProperty != null;
        oldProperty.setVisits(oldProperty.getVisits() + 1);
        propertyRepository.save(oldProperty);
        var property = propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property not found with id " + propertyId));
        var comments = commentsRepository.findByProperty(property)
                .stream()
                .map(comment -> new CommentsDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getFirstname()))
                        .toList();
        var propertyOwner = propertyOwnerRepository.findByProperties_Id(propertyId);
        var propertyOwnerDTO = new PropertyOwnerDTO(
                propertyOwner.getId(),
                propertyOwner.getFirstname()
        );
        return new PropertyDTO(
                property.getId(),
                property.getArea(),
                property.getBedrooms(),
                property.getBathrooms(),
                property.getDescription(),
                property.getFeatures(),
                property.getPrice(),
                property.getAddress(),
                property.getCoordinates(),
                property.getPropertyType(),
                property.getPropertyCondition(),
                property.getLocation(),
                property.getImages(),
                comments,
                propertyOwnerDTO);
    }

    public Long getPropertyVisits(Long propertyId){
        var property = propertyRepository.findById(propertyId).orElse(null);
        assert property != null;
        return property.getVisits();
    }

    public List<PropertyDTO> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyDTO> propertyDTOs = new ArrayList<>();
        for (Property property : properties) {
            PropertyDTO propertyDTO = new PropertyDTO(
                    property.getId(),
                    property.getArea(),
                    property.getBedrooms(),
                    property.getBathrooms(),
                    property.getDescription(),
                    property.getFeatures(),
                    property.getPrice(),
                    property.getAddress(),
                    property.getCoordinates(),
                    property.getPropertyType(),
                    property.getPropertyCondition(),
                    property.getLocation(),
                    property.getImages(),
                    property.getComments().stream().map(comment -> new CommentsDTO(
                                    comment.getId(),
                                    comment.getContent(),
                                    comment.getUser().getFirstname()))
                            .collect(Collectors.toList()),
                    new PropertyOwnerDTO(property.getPropertyOwner().getId(), property.getPropertyOwner().getFirstname())
            );
            propertyDTOs.add(propertyDTO);
        }
        return propertyDTOs;
    }

        public List<PropertyDTO> findPropertyByCriteriaSearch(@Nullable Integer bedrooms, @Nullable Integer bathrooms, @Nullable Double minPrice,@Nullable Double maxPrice, @Nullable PropertyType propertyType, @Nullable Density density, @Nullable String location) {
            List<Property> properties = propertyRepository.findBySearchCriteria(bedrooms, bathrooms, propertyType, minPrice, maxPrice, density, location);
            List<PropertyDTO> propertyDTOs = new ArrayList<>();
            for (Property property : properties) {
                PropertyDTO propertyDTO = new PropertyDTO(
                        property.getId(),
                        property.getArea(),
                        property.getBedrooms(),
                        property.getBathrooms(),
                        property.getDescription(),
                        property.getFeatures(),
                        property.getPrice(),
                        property.getAddress(),
                        property.getCoordinates(),
                        property.getPropertyType(),
                        property.getPropertyCondition(),
                        property.getLocation(),
                        property.getImages(),
                        property.getComments().stream().map(comment -> new CommentsDTO(
                                comment.getId(),
                                comment.getContent(),
                                comment.getUser().getFirstname()))
                                .collect(Collectors.toList()),
                        new PropertyOwnerDTO(property.getPropertyOwner().getId(), property.getPropertyOwner().getFirstname())
                );
                propertyDTOs.add(propertyDTO);
            }
            return propertyDTOs;
        }

    public List<PropertyDTO> getPropertiesByOwnerId(Long id) {
            var properties = propertyRepository.findByPropertyOwner_Id(id);
            List<PropertyDTO> propertyDTOs = new ArrayList<>();
            for (Property property : properties) {
                PropertyDTO propertyDTO = new PropertyDTO(
                        property.getId(),
                        property.getArea(),
                        property.getBedrooms(),
                        property.getBathrooms(),
                        property.getDescription(),
                        property.getFeatures(),
                        property.getPrice(),
                        property.getAddress(),
                        property.getCoordinates(),
                        property.getPropertyType(),
                        property.getPropertyCondition(),
                        property.getLocation(),
                        property.getImages(),
                        property.getComments().stream().map(comment -> new CommentsDTO(
                                        comment.getId(),
                                        comment.getContent(),
                                        comment.getUser().getFirstname()))
                                .collect(Collectors.toList()),
                        new PropertyOwnerDTO(property.getPropertyOwner().getId(), property.getPropertyOwner().getFirstname())
                );
                propertyDTOs.add(propertyDTO);
            }
            return propertyDTOs;
    }


    ///WITH TOKENS

    public List<Property> findPropertyByCriteriaSearchWithToken(@Nullable Integer bedrooms, @Nullable Integer bathrooms, @Nullable Double minPrice,@Nullable Double maxPrice, @Nullable PropertyType propertyType, @Nullable Density density, @Nullable String location){
        return propertyRepository.findBySearchCriteria(bedrooms, bathrooms, propertyType, minPrice, maxPrice, density, location);
    }

    public List<Property> getPropertiesByOwnerIdWithToken(Long id) {
        return propertyRepository.findByPropertyOwner_Id(id);
    }

    public List<Property> getAllPropertiesWithToken() {
        return propertyRepository.findAll();
    }

    public Property getPropertyWithToken(Long propertyId) {
        var property = propertyRepository.findById(propertyId).orElse(null);
        assert property != null;
        property.setVisits(property.getVisits() + 1);
        propertyRepository.save(property);
        return propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property not found with id " + propertyId));
    }
}
