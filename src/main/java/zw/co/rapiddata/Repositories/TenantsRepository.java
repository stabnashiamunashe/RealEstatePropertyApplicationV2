package zw.co.rapiddata.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.Tenant;

public interface TenantsRepository extends JpaRepository<Tenant,Long> {
    Tenant findByEmail(String email);


}
