package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    //Optional<Merchant> findOneByTransaction(Transaction transaction);
}