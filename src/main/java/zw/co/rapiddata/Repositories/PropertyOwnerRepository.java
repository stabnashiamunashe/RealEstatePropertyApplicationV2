package zw.co.rapiddata.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import zw.co.rapiddata.Models.PropertyOwner;

import java.time.LocalDate;
import java.util.List;

public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, Long> {
    PropertyOwner findByProperties_Id(Long id);
    PropertyOwner findByEmail(@NonNull String email);
    List<PropertyOwner> findBySubscriptionExpiryDateBefore(LocalDate currentDate);
    boolean existsByEmail(String email);

}
