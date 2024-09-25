package com.name.nameerpapitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.name.nameerpapitest.domain.NAMEUserDomain;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<NAMEUserDomain, Long> {
    Optional<NAMEUserDomain> findByUserName(String username);

    Boolean existsByUserName(String username);
    
    Boolean existsByEmail(String email);
}

