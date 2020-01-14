package com.progmasters.moovsmart.repository;

import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.domain.PropertyType;
import com.progmasters.moovsmart.domain.UserProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT p FROM Property p WHERE p.isValid = true order by p.id desc")
    List<Property> findAllByIsValid();

    List<Property> findAllByOwner(UserProperty user);

    @Query("select distinct p.city from Property p where p.city is not null and p.isValid = true")
    List<String> getAllCity();


    @Query("SELECT p FROM Property p " +
            "WHERE p.area BETWEEN :minArea AND :maxArea " +
            "AND p.price BETWEEN :minPrice AND :maxPrice " +
            "AND p.propertyType like case when :propertyType is not null then :propertyType else '%' end " +
            "AND p.propertyState like case when :propertyState is not null then :propertyState else '%' end " +
            "AND p.city like case when :city is not null then :city else '%' end " +
            "AND p.numberOfRooms = :numberOfRooms " +
            "AND p.isValid = true")
    List<Property> getFilteredProperties(
            @Param("minArea") double minArea, @Param("maxArea") double maxArea, @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            @Param("propertyState") PropertyState propertyState, @Param("propertyType") PropertyType propertyType,
            @Param("city") String city, @Param("numberOfRooms") int numberOfRooms
    );
}


