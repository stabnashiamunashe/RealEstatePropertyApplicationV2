package zw.co.rapiddata.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
