package com.progmasters.moovsmart.repository;


import com.progmasters.moovsmart.domain.UserProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProperty, Long> {
    Optional<UserProperty> findUserPropertiesByMail(String name);

}
