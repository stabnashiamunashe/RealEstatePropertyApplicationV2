package zw.co.rapiddata.Repositories;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import zw.co.rapiddata.Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    List<Property> findByPropertyOwner_Email(String email);
    List<Property> findByPropertyOwner_Id(Long id);
    List<Property> findAllByLocationId(Long id);

    List<Property> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Property> findByBedrooms(Integer bedrooms);

    List<Property> findByBathrooms(Integer bathroom);

    List<Property> findByPropertyType(PropertyType propertyType);

    default List<Property> findBySearchCriteria(Integer bedrooms,
                                                Integer bathrooms,
                                                PropertyType propertyType,
                                                Double minPrice,
                                                Double maxPrice,
                                                Density density,
                                                String location,
                                                City city
    ) {

        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (bedrooms != null) {
                predicates.add(criteriaBuilder.equal(root.get("bedrooms"), bedrooms));
            }
            if (bathrooms != null) {
                predicates.add(criteriaBuilder.equal(root.get("bathrooms"), bathrooms));
            }
            if (propertyType != null) {
                predicates.add(criteriaBuilder.equal(root.get("propertyType"), propertyType));
            }
            if (city != null) {
                predicates.add(criteriaBuilder.equal(root.get("city"), city));
            }
            if (density != null) {
                Join<Property, Location> join = root.join("location");
                predicates.add(criteriaBuilder.equal(join.get("density"), density));
            }

            if (location != null) {
                Join<Property, Location> join = root.join("location");
                predicates.add(criteriaBuilder.equal(join.get("name"), location));
            }
            if (minPrice != null && maxPrice != null) {
                predicates.add(criteriaBuilder.between(root.get("price"), minPrice, maxPrice));
            } else if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            } else if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }
}
