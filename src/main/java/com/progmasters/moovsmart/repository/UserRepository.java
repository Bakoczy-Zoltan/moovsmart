package com.progmasters.moovsmart.repository;


import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.dto.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProperty, Long> {
    Optional<UserProperty> findUserPropertiesByMail(String name);

    Optional<UserProperty> findAllByMail(String mail);
}
