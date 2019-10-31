package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.ApiUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("integration")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ApiUserRepositoryTest {

    @Autowired
    private ApiUserRepository apiUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Find ApiUser by Username")
    public void whenFindByNameThenReturnApiUser() {
        ApiUser defaultApiUser = new ApiUser();
        defaultApiUser.setUsername("demo@financialhouse.io");
        defaultApiUser.setPassword(passwordEncoder.encode("cjaiU8CV8443"));

        Optional<ApiUser> found = apiUserRepository.findOneByUsername(defaultApiUser.getUsername());

        assertEquals(defaultApiUser.getUsername(),
                found.get().getUsername(),
                "Problem with getting persisted apiuser");
    }

}
