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
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String imageName;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Property property;

    public Images(Property property, String imageName, String imageUrl) {
        this.property = property;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

}
