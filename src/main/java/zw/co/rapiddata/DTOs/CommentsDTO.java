package zw.co.rapiddata.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDTO {

    private Long id;

    private String content;

    private PropertyDTO property;

    private String firstname;

    public CommentsDTO(Long id, String content ,String firstname) {
        this.id = id;
        this.content = content;
        this.firstname = firstname;
    }
}
