package zw.co.rapiddata.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.rapiddata.Security.Models.Users;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tenants")
public class Tenant extends Users {
}
