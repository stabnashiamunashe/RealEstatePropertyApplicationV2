package zw.co.rapiddata.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.rapiddata.Config.Models.Users;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PropertyOwner extends Users {

    @OneToMany(mappedBy = "propertyOwner", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Property> properties;

}
