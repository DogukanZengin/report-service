package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.*;
import com.dz.financial.reporting.model.db.enums.AcquirerType;
import com.dz.financial.reporting.model.db.enums.Status;
import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Transaction CRUD Tests")
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("Query Transaction with ID")
    public void testQueryTransactionWithId() {
        Optional<Transaction> byId = transactionRepository.findById(999701L);
        assertAll(
                () -> assertTrue(byId.isPresent(), "Query byId(999701L) does not return Transaction"),
                () -> assertTrue(byId.get().getStatus().equals(Status.APPROVED)),
                () -> assertTrue(byId.get().getCode().equals("00")),
                () -> assertTrue(byId.get().getReferenceNo().equalsIgnoreCase("test-transaction-1")),
                () -> assertTrue(byId.get().getChannel().equalsIgnoreCase("API"))
        );
    }

    @Test
    @DisplayName("Query Transaction with transactionId")
    public void queryWithTransactionId() {
        Optional<Transaction> queriedTransaction = transactionRepository.
                findOneByIdAndDateAndMerchantId(999702L, 1539329656L, 99921L);
        assertAll(
                () -> assertTrue(queriedTransaction.isPresent()),
                () -> assertThat(queriedTransaction.get().getReferenceNo(), is(equalTo("test-transaction-2")))
        );
    }

    @Test
    @DisplayName("Create Transaction")
    public void testCreateTransaction() {
        AcquirerTransaction acquirerTransaction = AcquirerTransaction.builder().name("RoyalPay PayToCard")
                .code("RP")
                .type(AcquirerType.PAYTOCARD)
                .acquirerTransactionId(UUID.randomUUID().toString())
                .build();

        Agent agent = Agent.builder().customerIp("123.45.67.89")
                .customerUserAgent("PostmanRuntime/1.2.3")
                .merchantIp("12.34.56.78")
                .merchantUserAgent("PostmanRuntime/3.2.1").build();

        FxTransaction fxTransaction = FxTransaction.builder().originalAmount(new BigDecimal(1500))
                .originalCurrency("RUB")
                .convertedAmount(new BigDecimal(1500))
                .convertedCurrency("RUB")
                .fxTransactionId(UUID.randomUUID().toString())
                .build();

        IPN ipn = IPN.builder().sent(true)
                .token(UUID.randomUUID().toString())
                .type("MERCHANTIPN")
                .url("https://requestb.in/10bmd651")
                .build();

        Transaction transaction = new Transaction();
        transaction.setAcquirerTransaction(acquirerTransaction);
        transaction.setAgent(agent);
        transaction.setFxTransaction(fxTransaction);
        transaction.setIpn(ipn);

        acquirerTransaction.setTransaction(transaction);
        agent.setTransaction(transaction);
        fxTransaction.setTransaction(transaction);
        ipn.setTransaction(transaction);

        transaction.setReferenceNo("trn-test-seck-1");
        transaction.setChainId(UUID.randomUUID().toString());
        transaction.setReturnUrl(null);
        transaction.setStatus(Status.APPROVED);
        transaction.setCode("00");
        transaction.setMessage(WordUtils.capitalizeFully(Status.APPROVED.name()));
        transaction.setChannel("API");
        transaction.setType("AUTH");

        Transaction savedTransaction = transactionRepository.saveAndFlush(transaction);
        assertAll(
                () -> assertNotNull(savedTransaction),
                () -> assertNotNull(savedTransaction.getCreatedAt()),
                () -> assertNotNull(savedTransaction.getModifiedAt()),
                () -> assertTrue(savedTransaction.getAcquirerTransaction().getCode().equals("RP")),
                () -> assertTrue(savedTransaction.getAgent().getCustomerIp().equals("123.45.67.89")),
                () -> assertTrue(savedTransaction.getFxTransaction().getOriginalCurrency().equals("RUB")),
                () -> assertTrue(savedTransaction.getIpn().isSent())
        );
    }

    @Test
    @DisplayName("Update Transaction")
    public void testUpdateTransaction() {
        Optional<Transaction> byId = transactionRepository.findById(999703L);
        assertTrue(byId.isPresent(), "Query byId(999703L) does not return Transaction");
        Transaction transaction = byId.get();
        Date modifiedAt = transaction.getModifiedAt();
        transaction.setReturnUrl("returnurl-updated");
        Transaction updatedTransaction = transactionRepository.saveAndFlush(transaction);
        assertAll(
                () -> assertNotNull(updatedTransaction),
                () -> assertTrue(modifiedAt.before(updatedTransaction.getModifiedAt())),
                () -> assertTrue(updatedTransaction.getReturnUrl().equals("returnurl-updated"))
        );
    }

    @Test
    @DisplayName("Query For Reporting with Between Dates")
    public void testQueryForReportingWithBetweenDates() {
        Date startDate = Date.from(LocalDate.parse("2015-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(24)));
        List<Transaction> transactions = transactionRepository.queryAllForReporting(startDate, endDate
                , Optional.ofNullable(null), Optional.ofNullable(null));
        transactions.stream().forEach((t) ->
                assertAll(
                        () -> assertTrue(startDate.before(t.getCreatedAt())),
                        () -> assertTrue(endDate.after(t.getCreatedAt()))
                )
        );
    }

    @Test
    @DisplayName("Query For Reporting with Between Dates and Acquirer")
    public void testQueryForReportingWithBetweenDatesAndAcquirer() {
        Date startDate = Date.from(LocalDate.parse("2015-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(24)));
        List<Transaction> transactions = transactionRepository.queryAllForReporting(startDate, endDate
                , Optional.ofNullable(null), Optional.ofNullable(999304L));
        transactions.stream().forEach((t) ->
                assertAll(
                        () -> assertTrue(startDate.before(t.getCreatedAt())),
                        () -> assertTrue(endDate.after(t.getCreatedAt())),
                        () -> assertEquals(999304L, t.getAcquirerTransaction().getId())
                )
        );
    }

    @Test
    @DisplayName("Query For Reporting with Between Dates and Merchant")
    public void testQueryForReportingWithBetweenDatesAndMerchant() {
        Date startDate = Date.from(LocalDate.parse("2015-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(24)));
        List<Transaction> transactions = transactionRepository.queryAllForReporting(startDate, endDate
                , Optional.ofNullable(99921L), Optional.ofNullable(null));
        transactions.stream().forEach((t) ->
                assertAll(
                        () -> assertTrue(startDate.before(t.getCreatedAt())),
                        () -> assertTrue(endDate.after(t.getCreatedAt())),
                        () -> assertEquals(99921L, t.getMerchant().getId())
                )
        );
    }

    @Test
    @DisplayName("Query For Reporting with Between Dates and Acquirer and Merchant")
    public void testQueryForReportingWithBetweenDatesAndAcquirerAndMerchant() {
        Date startDate = Date.from(LocalDate.parse("2015-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(Instant.now().plus(Duration.ofHours(24)));
        List<Transaction> transactions = transactionRepository.queryAllForReporting(startDate, endDate
                , Optional.ofNullable(99922L), Optional.ofNullable(999304L));
        transactions.stream().forEach((t) ->
                assertAll(
                        () -> assertTrue(startDate.before(t.getCreatedAt())),
                        () -> assertTrue(endDate.after(t.getCreatedAt())),
                        () -> assertEquals(999304L, t.getAcquirerTransaction().getId()),
                        () -> assertEquals(99922L, t.getMerchant().getId())
                )
        );
    }
}