package com.dz.financial.reporting;

import com.dz.financial.reporting.model.db.ApiUser;
import com.dz.financial.reporting.repository.ApiUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootApplication
public class ReportingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportingApplication.class, args);
    }

    @Bean
    public CommandLineRunner insertApiUser(ApiUserRepository repository,
                                           @Value("${api-user.username}") String username,
                                           @Value("${api-user.password}") String password) {
        return (args) -> {
            ApiUser user = new ApiUser();
            user.setUsername(username);
            user.setPassword(passwordEncoder().encode(password));
            repository.save(user);
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
