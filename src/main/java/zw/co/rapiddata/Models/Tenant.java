package zw.co.rapiddata.Models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.rapiddata.Config.Models.Users;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tenant extends Users {
}
