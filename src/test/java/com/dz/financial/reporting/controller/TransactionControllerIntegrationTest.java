package com.dz.financial.reporting.controller;

import com.dz.financial.reporting.model.db.enums.Status;
import com.dz.financial.reporting.model.rest.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Transaction Controller Integration Tests")
@Execution(ExecutionMode.CONCURRENT)
public class TransactionControllerIntegrationTest extends AbstractControllerTest {
    @Value("${jwt.expiration}")
    private long expiration;

    @Test
    @DisplayName("Query Transaction with existing transactionId")
    public void testQueryTransactionWithTransactionId() throws URISyntaxException {
        ResponseEntity<TransactionQueryResponse> httpResponse = queryByTransactionId("999702-1539329656-99921");
        TransactionQueryResponse response = httpResponse.getBody();

        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNotNull(response.getCustomerInfo()),
                () -> assertNotNull(response.getFx()),
                () -> assertNotNull(response.getTransaction()),
                () -> assertNotNull(response.getTransaction().getTransactionMerchant()),
                () -> assertNotNull(response.getTransaction().getTransactionMerchant().getAgent()),
                () -> assertNotNull(response.getMerchant()),
                () -> assertThat(response.getCustomerInfo().getNumber(), is(equalTo(99911L))),
                () -> assertThat(response.getTransaction().getTransactionMerchant().getId(), is(equalTo(999702L)))
        );
    }

    @Test
    @DisplayName("Query Transaction with non-existing transactionId")
    public void testQueryTransactionWithNonExistingTransactionId() throws URISyntaxException {
        ResponseEntity<TransactionQueryResponse> httpResponse = queryByTransactionId("123-456-789");
        TransactionQueryResponse response = httpResponse.getBody();

        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNull(response)
        );
    }

    @Test
    @DisplayName("Query Transaction with not well-formatted split")
    public void testQueryTransactionWithNotWellFormattedSplit() throws URISyntaxException {
        ResponseEntity<TransactionQueryResponse> httpResponse = queryByTransactionId("999702_1539329656_99921");
        TransactionQueryResponse response = httpResponse.getBody();

        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNull(response)
        );
    }

    @Test
    @DisplayName("Query Transaction with not well-formatted tokens")
    public void testQueryTransactionWithNotWellFormattedTokens() throws URISyntaxException {
        ResponseEntity<TransactionQueryResponse> httpResponse = queryByTransactionId("abc-def-ghi");
        TransactionQueryResponse response = httpResponse.getBody();

        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNull(response)
        );
    }

    @Test
    @DisplayName("Get Report with Between Dates For No Data")
    public void testQueryForReportingWithBetweenDatesForNoData() throws URISyntaxException {
        Date startDate = Date.from(Instant.now().plus(Duration.ofHours(24)));
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(72)));

        ReportRequest reportRequest = new ReportRequest(startDate, endDate, null, null);
        ResponseEntity<ReportResponse> httpResponse = getReport(reportRequest);
        ReportResponse reportResponse = httpResponse.getBody();
        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNull(reportResponse)
        );
    }

    @Test
    @DisplayName("Get Report with Between Dates")
    public void testQueryForReportingWithBetweenDates() throws URISyntaxException {
        Date startDate = Date.from(LocalDate.parse("2015-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(24)));

        ReportRequest reportRequest = new ReportRequest(startDate, endDate, null, null);
        ResponseEntity<ReportResponse> httpResponse = getReport(reportRequest);
        ReportResponse reportResponse = httpResponse.getBody();
        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNotNull(reportResponse),
                () -> assertNotNull(reportResponse.getStatus()),
                () -> assertNotNull(reportResponse.getReportData()),
                () -> assertEquals(Status.APPROVED, reportResponse.getStatus()),
                () -> assertEquals(3, reportResponse.getReportData().size())
        );
        reportResponse.getReportData().forEach(item -> {
            switch (item.getCurrency()) {
                case "TRY": {
                    assertAll(
                            () -> assertEquals(1, item.getCount()),
                            () -> assertEquals("870.00", item.getTotal())
                    );
                    break;
                }
                case "USD": {
                    assertAll(
                            () -> assertEquals(1, item.getCount()),
                            () -> assertEquals("1000.00", item.getTotal())
                    );
                    break;
                }
                case "GBP": {
                    assertAll(
                            () -> assertEquals(2, item.getCount()),
                            () -> assertEquals("800.00", item.getTotal())
                    );
                    break;
                }
                case "RUB": {
                    assertAll(
                            () -> assertEquals(1, item.getCount()),
                            () -> assertEquals("1500.00", item.getTotal())
                    );
                    break;
                }
                default: {
                    fail("Please check the init data!");
                }
            }
        });
    }

    @Test
    @DisplayName("Get Report with Between Dates and Merchant")
    public void testQueryForReportingWithBetweenDatesAndMerchant() throws URISyntaxException {
        Date startDate = Date.from(LocalDate.parse("2015-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(24)));

        ReportRequest reportRequest = new ReportRequest(startDate, endDate, 99921L, null);
        ResponseEntity<ReportResponse> httpResponse = getReport(reportRequest);
        ReportResponse reportResponse = httpResponse.getBody();
        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNotNull(reportResponse),
                () -> assertNotNull(reportResponse.getStatus()),
                () -> assertNotNull(reportResponse.getReportData()),
                () -> assertEquals(Status.APPROVED, reportResponse.getStatus()),
                () -> assertEquals(3, reportResponse.getReportData().size())
        );
        reportResponse.getReportData().forEach(item -> {
            switch (item.getCurrency()) {
                case "TRY": {
                    assertAll(
                            () -> assertEquals(1, item.getCount()),
                            () -> assertEquals("870.00", item.getTotal())
                    );
                    break;
                }
                case "USD": {
                    assertAll(
                            () -> assertEquals(1, item.getCount()),
                            () -> assertEquals("1000.00", item.getTotal())
                    );
                    break;
                }
                case "GBP": {
                    assertAll(
                            () -> assertEquals(1, item.getCount()),
                            () -> assertEquals("500.00", item.getTotal())
                    );
                    break;
                }
                default: {
                    fail("Please check the init data!");
                }
            }
        });
    }

    @Test
    @DisplayName("Get Report with Between Dates and Acquirer")
    public void testQueryForReportingWithBetweenDatesAndAcquirer() throws URISyntaxException {
        Date startDate = Date.from(LocalDate.parse("2015-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(24)));

        ReportRequest reportRequest = new ReportRequest(startDate, endDate, null, 999304L);
        ResponseEntity<ReportResponse> httpResponse = getReport(reportRequest);
        ReportResponse reportResponse = httpResponse.getBody();
        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNotNull(reportResponse),
                () -> assertNotNull(reportResponse.getStatus()),
                () -> assertNotNull(reportResponse.getReportData()),
                () -> assertEquals(Status.APPROVED, reportResponse.getStatus()),
                () -> assertEquals(1, reportResponse.getReportData().size())
        );
        reportResponse.getReportData().forEach(item -> {
            switch (item.getCurrency()) {
                case "GBP": {
                    assertAll(
                            () -> assertEquals(1, item.getCount()),
                            () -> assertEquals("300.00", item.getTotal())
                    );
                    break;
                }
                default: {
                    fail("Please check the init data!");
                }
            }
        });
    }

    @Test
    @DisplayName("Get Report with Between Dates and Acquirer and Merchant")
    public void testQueryForReportingWithBetweenDatesAndAcquirerAndMerchant() throws URISyntaxException {
        Date startDate = Date.from(LocalDate.parse("2015-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(24)));

        ReportRequest reportRequest = new ReportRequest(startDate, endDate, 99922L, 999304L);
        ResponseEntity<ReportResponse> httpResponse = getReport(reportRequest);
        ReportResponse reportResponse = httpResponse.getBody();
        assertAll(
                () -> assertThat(httpResponse.getStatusCodeValue(), is(equalTo(200))),
                () -> assertNotNull(reportResponse),
                () -> assertNotNull(reportResponse.getStatus()),
                () -> assertNotNull(reportResponse.getReportData()),
                () -> assertEquals(Status.APPROVED, reportResponse.getStatus()),
                () -> assertEquals(1, reportResponse.getReportData().size())
        );
        reportResponse.getReportData().forEach(item -> {
            switch (item.getCurrency()) {
                case "GBP": {
                    assertAll(
                            () -> assertEquals(1, item.getCount()),
                            () -> assertEquals("300.00", item.getTotal())
                    );
                    break;
                }
                default: {
                    fail("Please check the init data!");
                }
            }
        });
    }

    private ResponseEntity<TransactionQueryResponse> queryByTransactionId(String transactionId) throws URISyntaxException {
        RestTemplate restTemplate = restTemplate();
        URI uri = new URI(createURLWithPort("/transactions"));
        JwtResponse jwtResponse = login("demo@financialhouse.io", "cjaiU8CV8443");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtResponse.getToken());
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        TransactionQueryRequest request = new TransactionQueryRequest(transactionId);

        HttpEntity<TransactionQueryRequest> httpRequest = new HttpEntity<>(request, headers);

        return restTemplate.postForEntity(
                uri, httpRequest, TransactionQueryResponse.class);
    }

    private ResponseEntity<ReportResponse> getReport(ReportRequest reportRequest) throws URISyntaxException {
        RestTemplate restTemplate = restTemplate();
        URI uri = new URI(createURLWithPort("/transactions/report"));
        JwtResponse jwtResponse = login("demo@financialhouse.io", "cjaiU8CV8443");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtResponse.getToken());
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<ReportRequest> httpRequest = new HttpEntity<>(reportRequest, headers);

        return restTemplate.postForEntity(uri, httpRequest, ReportResponse.class);
    }
}
