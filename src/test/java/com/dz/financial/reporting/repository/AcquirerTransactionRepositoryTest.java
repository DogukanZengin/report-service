package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.AcquirerTransaction;
import com.dz.financial.reporting.model.db.enums.AcquirerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("integrationtest")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("AcquirerTransaction CRUD Tests")
public class AcquirerTransactionRepositoryTest {

    @Autowired
    private AcquirerTransactionRepository acquirerTransactionRepository;

    @Test
    @DisplayName("Query AcquirerTransaction with ID")
    public void testQueryAcquirerTransactionWithId() {
        Optional<AcquirerTransaction> byId = acquirerTransactionRepository.findById(999301L);
        assertAll(
                () -> assertTrue(byId.isPresent(), "Query byId(999301L) does not return AcquirerTransaction"),
                () -> assertTrue(byId.get().getName().equalsIgnoreCase("RoyalPay PayToCard")),
                () -> assertTrue(byId.get().getCode().equalsIgnoreCase("RP")),
                () -> assertTrue(byId.get().getType() == AcquirerType.PAYTOCARD),
                () -> assertTrue(byId.get().getAcquirerTransactionId().equalsIgnoreCase("999301RP0"))
        );
    }
}