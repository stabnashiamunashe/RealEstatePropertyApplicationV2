package zw.co.rapiddata.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.rapiddata.Models.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedPropertyDTO {

    private Long id;

    private String area;

    private String address;

    private Integer bedrooms;

    private Integer bathrooms;

    private String description;

    private String features;

    private Double price;

    private Long visits;
    private Coordinates coordinates;

    private PropertyType propertyType;

    private Conditions propertyCondition;

    private LocalDateTime subscriptionExpiryDate;

    private Location location;

    private List<Images> images;

    private List<Comments> comments;

    private PropertyOwner propertyOwner;

    public AuthenticatedPropertyDTO(Long id, String area, String address, Integer bedrooms, Integer bathrooms, String description, String features, Double price, Long visits, Coordinates coordinates, PropertyType propertyType, Conditions propertyCondition, LocalDateTime subscriptionExpiryDate, Location location, List<Images> images, List<Comments> comments, PropertyOwner propertyOwner) {
        this.id = id;
        this.area = area;
        this.address = address;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.description = description;
        this.features = features;
        this.price = price;
        this.visits = visits;
        this.coordinates = coordinates;
        this.propertyType = propertyType;
        this.propertyCondition = propertyCondition;
        this.subscriptionExpiryDate = subscriptionExpiryDate;
        this.location = location;
        this.images = images;
        this.comments = comments;
        this.propertyOwner = propertyOwner;
    }
}
