package zw.co.rapiddata.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.OwnershipDocuments;

import java.util.List;

public interface OwnershipDocumentsRepository extends JpaRepository<OwnershipDocuments, Long> {
    List<OwnershipDocuments> findByProperty_Id(Long id);


}
