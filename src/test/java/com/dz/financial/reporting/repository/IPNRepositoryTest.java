package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.IPN;
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
@DisplayName("IPN CRUD Tests")
public class IPNRepositoryTest {

    @Autowired
    private IPNRepository ipnRepository;

    @Test
    @DisplayName("Query IPN with ID")
    public void testQueryIPNWithId() {
        Optional<IPN> byId = ipnRepository.findById(999601L);
        assertAll(
                () -> assertTrue(byId.isPresent(), "Query byId(999601L) does not return IPN"),
                () -> assertTrue(byId.get().isSent()),
                () -> assertTrue(byId.get().getUrl().equalsIgnoreCase("https://requestb.in/999601")),
                () -> assertTrue(byId.get().getType().equalsIgnoreCase("MERCHANTIPN")),
                () -> assertTrue(byId.get().getToken().equalsIgnoreCase("999601MERCHANTIPN"))
        );
    }
}