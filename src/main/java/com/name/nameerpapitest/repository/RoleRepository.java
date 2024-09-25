package com.name.nameerpapitest.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.name.nameerpapitest.domain.AppRole;
import com.name.nameerpapitest.domain.NAMERolesDomain;

public interface RoleRepository extends JpaRepository<NAMERolesDomain, Integer> {
    Optional<NAMERolesDomain> findByRoleName(AppRole appRole);

}