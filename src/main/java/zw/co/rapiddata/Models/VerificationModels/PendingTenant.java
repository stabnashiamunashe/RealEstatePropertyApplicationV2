package zw.co.rapiddata.Models.VerificationModels;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.rapiddata.Models.Tenant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PendingTenant extends Tenant {
    private int verificationCode;

}
