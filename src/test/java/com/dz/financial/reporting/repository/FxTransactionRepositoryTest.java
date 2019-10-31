package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.FxTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integrationtest")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("FxTransaction CRUD Tests")
public class FxTransactionRepositoryTest {

    @Autowired
    private FxTransactionRepository fxTransactionRepository;

    @Test
    @DisplayName("Query FxTransaction with ID")
    public void testQueryFxTransactionWithId() {
        Optional<FxTransaction> byId = fxTransactionRepository.findById(999501L);
        assertAll(
                () -> assertTrue(byId.isPresent(), "Query byId(999501L) does not return FxTransaction"),
                () -> assertEquals(0, byId.get().getOriginalAmount().compareTo(new BigDecimal(1000))),
                () -> assertTrue(byId.get().getOriginalCurrency().equalsIgnoreCase("USD")),
                () -> assertEquals(0, byId.get().getConvertedAmount().compareTo(new BigDecimal(1000))),
                () -> assertTrue(byId.get().getConvertedCurrency().equalsIgnoreCase("USD")),
                () -> assertTrue(byId.get().getFxTransactionId().equalsIgnoreCase("FX-999501"))
        );
    }
}