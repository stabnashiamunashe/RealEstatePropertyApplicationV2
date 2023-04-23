package zw.co.rapiddata.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private City city;

    private String name;

    @Enumerated(EnumType.STRING)
    private Density density;

    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Property> properties;
}
