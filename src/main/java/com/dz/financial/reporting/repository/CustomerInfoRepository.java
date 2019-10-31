package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {
    Optional<CustomerInfo> findOneByEmail(String email);
}