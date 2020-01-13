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

/*
declare @min_area int
declare @max_area int
declare @min_price int
declare @max_price int
declare @property_state string
declare @property_type string
declare @city string
declare @number_of_rooms int

SELECT *
FROM Property AS P
WHERE P.Area BETWEEN @min_area AND @max_area
        AND P.Price BETWEEN @min_price AND @max_price
        AND P.Property_type = @property_type
        AND P.Property_state = @property_state
        AND P.City = @city
        AND P.number_of_rooms = @number_of_rooms
        AND P.isValid = true
ORDER BY P.Date DESC;*/


/*....AND (IF p.city IS NULL THEN p.city IN (SELECT city FROM property)
            ELSE p.city = :city)   */
