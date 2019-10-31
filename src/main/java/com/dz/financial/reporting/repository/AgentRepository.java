package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.Agent;
import com.dz.financial.reporting.model.db.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findOneByTransaction(Transaction transaction);
}