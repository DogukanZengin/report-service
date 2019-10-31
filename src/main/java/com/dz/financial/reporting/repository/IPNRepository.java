package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.IPN;
import com.dz.financial.reporting.model.db.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPNRepository extends JpaRepository<IPN, Long> {
    Optional<IPN> findOneByTransaction(Transaction transaction);
}