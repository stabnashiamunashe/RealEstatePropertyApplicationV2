package zw.co.rapiddata.Models.VerificationModels.VerificationRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.VerificationModels.PendingTenant;

public interface PendingTenantRepository extends JpaRepository<PendingTenant, Long> {
    long deleteByEmail(String email);
    boolean existsByEmail(String email);
    PendingTenant findByEmail(String email);
}
