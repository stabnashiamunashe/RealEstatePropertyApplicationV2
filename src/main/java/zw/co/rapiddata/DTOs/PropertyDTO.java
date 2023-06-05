package zw.co.rapiddata.DTOs;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.rapiddata.Models.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {

    private Long id;
    private String area;
    private Integer bedrooms;
    private Integer bathrooms;
    private String description;
    private Double price;
    @Embedded
    private Coordinates coordinates;
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    @Enumerated(EnumType.STRING)
    private Conditions propertyCondition;
    private Location location;
    private List<Images> images;
    private List<CommentsDTO> comments;
    private PropertyOwnerDTO propertyOwner;

    public PropertyDTO(Long id, String area, Integer bedrooms, Integer bathrooms, String description, String features, Double price, String address, Coordinates coordinates, PropertyType propertyType, Conditions propertyCondition, Location location, List<Images> images, List<CommentsDTO> comments, PropertyOwnerDTO propertyOwner) {
        this.id = id;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.description = description;
        this.price = price;
        this.coordinates = coordinates;
        this.propertyType = propertyType;
        this.propertyCondition = propertyCondition;
        this.location = location;
        this.images = images;
        this.comments = comments;
        this.propertyOwner = propertyOwner;
    }

    public PropertyDTO(Long id, String area, Integer bedrooms, Integer bathrooms, String description, Double price, String address, Coordinates coordinates, PropertyType propertyType, Conditions propertyCondition, Location location, List<Images> images, PropertyOwnerDTO propertyOwner) {
        this.id = id;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.description = description;
        this.price = price;
        this.coordinates = coordinates;
        this.propertyType = propertyType;
        this.propertyCondition = propertyCondition;
        this.location = location;
        this.images = images;
        this.propertyOwner = propertyOwner;
    }

    public PropertyDTO(Long id, String area, Integer bedrooms, Integer bathrooms, String description, Double price, String address, Coordinates coordinates, PropertyType propertyType, Conditions propertyCondition, Location location, List<Images> images, List<CommentsDTO> comments, PropertyOwnerDTO propertyOwnerDTO) {
        this.id = id;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.description = description;
        this.price = price;
        this.coordinates = coordinates;
        this.propertyType = propertyType;
        this.propertyCondition = propertyCondition;
        this.location = location;
        this.images = images;
        this.comments = comments;
        this.propertyOwner = propertyOwnerDTO;
    }
}
