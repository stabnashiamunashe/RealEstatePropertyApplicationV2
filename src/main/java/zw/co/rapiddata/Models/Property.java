package zw.co.rapiddata.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String area;

    private String address;

    private Integer bedrooms;

    private Integer bathrooms;

    private String description;

    private String features;

    private Double price;

    private Boolean hasBorehole;

    private Integer garagesCarSpots;

    private Integer stories;

    private Boolean hasInternetConnection;

    private Boolean allowsPets;

    private Boolean hasZesa;

    private Boolean hasElectricFence;

    private Boolean hasElectricGate;

    private Boolean hasLandscapedGarden;

    private Boolean hasSwimmingPool;

    private Boolean hasVeranda;

    private Boolean isWalledOrFenced;

    private Boolean hasWaterTanks;

    private Boolean hasAlarmSystem;

    private Boolean hasBuiltInCupboards;

    private Boolean hasCourtyard;

    private Boolean hasFireplace;

    private Boolean hasFittedKitchen;

    private Boolean hasMES;

    private Boolean hasSolarGeyser;

    private Boolean isTiled;

    private Boolean hasWalkInCloset;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long visits;

    private Boolean active;

    @Embedded
    private Coordinates coordinates;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private Conditions propertyCondition;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime subscriptionExpiryDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
    private List<Images> images;

    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TitleDeeds> titleDeeds;

    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comments> comments;

    @ManyToOne
    @JoinColumn(name = "property_owner_id")
    private PropertyOwner propertyOwner;

}
