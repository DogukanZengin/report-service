package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findOneByIdAndDateAndMerchantId(long id, long date, long merchantId);

    @Query(value = "SELECT t FROM Transaction t WHERE createdAt BETWEEN :startDate AND :toDate"
            + " AND (:merchantId IS NULL OR t.merchant.id = :merchantId)"
            + " AND (:acquirerId IS NULL OR t.acquirerTransaction.id = :acquirerId)"
            + " AND (t.status = 0)")
    List<Transaction> queryAllForReporting(@Param("startDate") Date startDate,
                                           @Param("toDate") Date toDate,
                                           @Param("merchantId") Optional<Long> merchantId,
                                           @Param("acquirerId") Optional<Long> acquirerId);
}