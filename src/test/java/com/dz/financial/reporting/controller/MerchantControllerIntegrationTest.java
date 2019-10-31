package com.dz.financial.reporting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dz.financial.reporting.exceptions.AuthenticationErrorResponse;
import com.dz.financial.reporting.model.db.enums.Status;
import com.dz.financial.reporting.model.rest.JwtResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Merchant Controller Integration Tests")
@Execution(ExecutionMode.CONCURRENT)
public class MerchantControllerIntegrationTest extends AbstractControllerTest {
    @Value("${jwt.expiration}")
    private long expiration;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Login Failed - No Input")
    public void testLoginFailedWithoutInput() throws URISyntaxException, IOException {
        try {
            login(null, null);
        } catch (HttpStatusCodeException e) {
            AuthenticationErrorResponse aer = objectMapper.readValue(e.getResponseBodyAsString(), AuthenticationErrorResponse.class);
            assertAll(
                    () -> assertThat(e.getRawStatusCode(), is(equalTo(500))),
                    () -> assertThat(aer.getCode().intValue(), is(equalTo(0))),
                    () -> assertThat(aer.getStatus(), is(equalTo(Status.DECLINED))),
                    () -> assertThat(aer.getMessage(), is(equalTo("Merchant User credentials are not valid")))
            );
        }
    }

    @Test
    @DisplayName("Login Failed - Invalid Password")
    public void testLoginFailedDueToInvalidPassword() throws URISyntaxException, IOException {
        try {
            login("dummy@mail.com", "111111");
        } catch (HttpStatusCodeException e) {
            AuthenticationErrorResponse aer = objectMapper.readValue(e.getResponseBodyAsString(), AuthenticationErrorResponse.class);
            assertAll(
                    () -> assertThat(e.getRawStatusCode(), is(equalTo(500))),
                    () -> assertThat(aer.getCode().intValue(), is(equalTo(0))),
                    () -> assertThat(aer.getStatus(), is(equalTo(Status.DECLINED))),
                    () -> assertThat(aer.getMessage(), is(equalTo("Merchant User credentials are not valid")))
            );
        }
    }

    @Test
    @DisplayName("Login Failed - User not found")
    public void testLoginFailedDueToNonExistingUser() throws URISyntaxException, IOException {
        try {
            login("dummy@mail.com", "111111");
        } catch (HttpStatusCodeException e) {
            AuthenticationErrorResponse aer = objectMapper.readValue(e.getResponseBodyAsString(), AuthenticationErrorResponse.class);
            assertAll(
                    () -> assertThat(e.getRawStatusCode(), is(equalTo(500))),
                    () -> assertThat(aer.getCode().intValue(), is(equalTo(0))),
                    () -> assertThat(aer.getStatus(), is(equalTo(Status.DECLINED))),
                    () -> assertThat(aer.getMessage(), is(equalTo("Merchant User credentials are not valid")))
            );
        }
    }

    @Test
    @DisplayName("Login Success")
    public void testLoginSuccess() throws URISyntaxException {
        JwtResponse jwtResponse = login("demo@financialhouse.io", "cjaiU8CV8443");
        assertAll(
                () -> assertThat(jwtResponse.getToken(), not(emptyString())),
                () -> assertThat(jwtResponse.getStatus(), is(equalTo(Status.APPROVED)))
        );
    }
}
