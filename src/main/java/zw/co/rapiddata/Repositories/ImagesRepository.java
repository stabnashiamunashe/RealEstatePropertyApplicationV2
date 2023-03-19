package zw.co.rapiddata.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.Images;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {

    List<Images> findImagesByPropertyId(Long propertyId);
}
