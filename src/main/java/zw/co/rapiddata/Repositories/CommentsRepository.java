package zw.co.rapiddata.Repositories;

import com.azure.core.http.HttpHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.Comments;
import zw.co.rapiddata.Models.Property;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByPropertyId(Long propertyId);

    List<Comments> findByProperty(Property property);
}
