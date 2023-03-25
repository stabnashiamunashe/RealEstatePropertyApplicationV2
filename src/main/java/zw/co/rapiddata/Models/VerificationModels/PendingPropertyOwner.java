package zw.co.rapiddata.Models.VerificationModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.rapiddata.Models.PropertyOwner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PendingPropertyOwner extends PropertyOwner {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int verificationCode;

}
