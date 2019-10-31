package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.FxTransaction;
import com.dz.financial.reporting.model.db.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FxTransactionRepository extends JpaRepository<FxTransaction, Long> {
    Optional<FxTransaction> findOneByTransaction(Transaction transaction);
}