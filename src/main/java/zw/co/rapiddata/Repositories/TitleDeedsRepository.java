package zw.co.rapiddata.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.rapiddata.Models.TitleDeeds;

import java.util.List;

public interface TitleDeedsRepository extends JpaRepository<TitleDeeds, Long> {
    List<TitleDeeds> findByProperty_Id(Long id);


}
