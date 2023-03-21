package zw.co.rapiddata.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PropertyOwnerDTO {

    private Long id;
    private String firstname;
    private List<CommentsDTO> comments;
    private List<PropertyDTO> properties;

    public PropertyOwnerDTO(Long id, String firstname) {
        this.id = id;
        this.firstname = firstname;
    }
}
