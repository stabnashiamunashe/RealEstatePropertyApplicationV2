package zw.co.rapiddata.Models.VerificationModels.VerificationRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.VerificationModels.PendingPropertyOwner;

public interface PendingPropertyOwnerRepository extends JpaRepository<PendingPropertyOwner, Long> {
    PendingPropertyOwner findByEmail(String email);
}
