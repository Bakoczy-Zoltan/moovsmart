package com.progmasters.moovsmart.repository;

import com.progmasters.moovsmart.security.TokenStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenStorageRepository extends JpaRepository<TokenStorage, Long> {
    Optional<TokenStorage> findByActiveToken(String token);
}
