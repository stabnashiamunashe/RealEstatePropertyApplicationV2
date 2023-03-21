package zw.co.rapiddata.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstname;

    public UserDTO(Long id, String firstname) {
        this.id = id;
        this.firstname = firstname;
    }
}
