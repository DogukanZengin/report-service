package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.AcquirerTransaction;
import com.dz.financial.reporting.model.db.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcquirerTransactionRepository extends JpaRepository<AcquirerTransaction, Long> {
    Optional<AcquirerTransaction> findOneByTransaction(Transaction transaction);
}