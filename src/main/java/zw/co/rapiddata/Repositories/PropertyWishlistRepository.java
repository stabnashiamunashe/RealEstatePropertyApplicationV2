package zw.co.rapiddata.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.PropertyWishlist;

import java.util.List;

public interface PropertyWishlistRepository extends JpaRepository<PropertyWishlist, Long> {
    List<PropertyWishlist> findByTenantId(Long tenantId);
}
