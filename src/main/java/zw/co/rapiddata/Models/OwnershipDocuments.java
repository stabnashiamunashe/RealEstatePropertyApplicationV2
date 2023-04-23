package zw.co.rapiddata.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OwnershipDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PropertyOwnershipType propertyOwnershipType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String uploadedName;

    private String blobName;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Property property;

    public OwnershipDocuments(String uploadedName, String blobName, Property property) {
        this.uploadedName = uploadedName;
        this.blobName = blobName;
        this.property = property;
    }
}
