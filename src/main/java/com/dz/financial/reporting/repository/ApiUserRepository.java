package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
    Optional<ApiUser> findOneByUsername(String username);
}