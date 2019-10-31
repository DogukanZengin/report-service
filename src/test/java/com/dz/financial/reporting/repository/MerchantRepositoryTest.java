package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.Merchant;
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
@DisplayName("Merchant CRUD Tests")
public class MerchantRepositoryTest {

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    @DisplayName("Query Merchant with ID")
    public void testQueryMechantWithId() {
        Optional<Merchant> byId = merchantRepository.findById(99921L);
        assertAll(
                () -> assertTrue(byId.isPresent(), "Query byId(99921L) does not return Merchant"),
                () -> assertTrue(byId.get().getName().equalsIgnoreCase("MERCHANT1")),
                () -> assertTrue(byId.get().isAllowPartialRefund()),
                () -> assertTrue(byId.get().isAllowPartialCapture())
        );
    }
}