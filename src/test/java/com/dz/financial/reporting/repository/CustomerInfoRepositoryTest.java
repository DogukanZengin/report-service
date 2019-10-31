package com.dz.financial.reporting.repository;

import com.dz.financial.reporting.model.db.CustomerInfo;
import com.dz.financial.reporting.model.db.enums.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integrationtest")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("CustomerInfo CRUD Tests")
public class CustomerInfoRepositoryTest {

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Test
    @DisplayName("Query CustomerInfo with ID")
    public void testQueryCustomerInfoWithId() {
        Optional<CustomerInfo> byId = customerInfoRepository.findById(99911L);
        assertAll(
                () -> assertTrue(byId.isPresent(), "Query byId(99911L) does not return CustomerInfo"),
                () -> assertTrue(byId.get().getBillingFirstName().equalsIgnoreCase("BILL_FIRSTNAME1"))
        );
    }

    @Test
    @DisplayName("Query CustomerInfo with email")
    public void testQueryCustomerInfoWithEmail() {
        Optional<CustomerInfo> byId = customerInfoRepository.findOneByEmail("customer1@mail.com");
        assertAll(
                () -> assertTrue(byId.isPresent(), "Query byEmail(customer1@mail.com) does not return CustomerInfo"),
                () -> assertTrue(byId.get().getEmail().equalsIgnoreCase("customer1@mail.com"))
        );
    }

    @Test
    @DisplayName("Update CustomerInfo")
    public void testUpdateCustomerInfo() {
        Optional<CustomerInfo> byId = customerInfoRepository.findById(99912L);
        assertTrue(byId.isPresent(), "Query byId(99912L) does not return CustomerInfo");
        CustomerInfo customerInfo = byId.get();
        Date modifiedAt = customerInfo.getModifiedAt();
        customerInfo.setShippingAddress1("SHIP_FIRSTNAME2_UPDATED");
        CustomerInfo updatedCustomerInfo = customerInfoRepository.saveAndFlush(customerInfo);
        assertAll(
                () -> assertNotNull(updatedCustomerInfo),
                () -> assertTrue(modifiedAt.before(updatedCustomerInfo.getModifiedAt())),
                () -> assertTrue(updatedCustomerInfo.getShippingAddress1().equals("SHIP_FIRSTNAME2_UPDATED"))
        );
    }

    @Test
    @DisplayName("Basic CustomerInfo Save With Builder")
    public void testSaveCustomerInfo() {
        CustomerInfo ci = generateCustomerInfo();
        CustomerInfo saveCi = customerInfoRepository.save(ci);

        assertAll(
                () -> assertThat(saveCi.getCustomerNumber(), is(equalTo(ci.getCustomerNumber()))),
                () -> assertThat(saveCi.getExpiryMonth(), is(equalTo(ci.getExpiryMonth()))),
                () -> assertThat(saveCi.getExpiryYear(), is(equalTo(ci.getExpiryYear()))),
                () -> assertThat(saveCi.getEmail(), is(equalTo(ci.getEmail()))),
                () -> assertThat(saveCi.getBillingTitle(), is(equalTo(ci.getBillingTitle()))),
                () -> assertThat(saveCi.getBillingFirstName(), is(equalTo(ci.getBillingFirstName()))),
                () -> assertThat(saveCi.getBillingLastName(), is(equalTo(ci.getBillingLastName()))),
                () -> assertThat(saveCi.getBillingCompany(), is(equalTo(ci.getBillingCompany()))),
                () -> assertThat(saveCi.getBillingAddress1(), is(equalTo(ci.getBillingAddress1()))),
                () -> assertThat(saveCi.getBillingCity(), is(equalTo(ci.getBillingCity()))),
                () -> assertThat(saveCi.getBillingPostcode(), is(equalTo(ci.getBillingPostcode()))),
                () -> assertThat(saveCi.getBillingState(), is(equalTo(ci.getBillingState()))),
                () -> assertThat(saveCi.getBillingCountry(), is(equalTo(ci.getBillingCountry()))),
                () -> assertThat(saveCi.getBillingPhone(), is(equalTo(ci.getBillingPhone()))),
                () -> assertThat(saveCi.getStartMonth(), is(equalTo(ci.getStartMonth()))),
                () -> assertThat(saveCi.getIssueNumber(), is(equalTo(ci.getIssueNumber()))),
                () -> assertThat(saveCi.getBirthday(), is(equalTo(ci.getBirthday()))),
                () -> assertThat(saveCi.getGender(), is(equalTo(ci.getGender()))),
                () -> assertThat(saveCi.getBillingAddress2(), is(equalTo(ci.getBillingAddress2()))),
                () -> assertThat(saveCi.getBillingFax(), is(equalTo(ci.getBillingFax()))),
                () -> assertThat(saveCi.getShippingTitle(), is(equalTo(ci.getShippingTitle()))),
                () -> assertThat(saveCi.getShippingFirstName(), is(equalTo(ci.getShippingFirstName()))),
                () -> assertThat(saveCi.getShippingLastName(), is(equalTo(ci.getShippingLastName()))),
                () -> assertThat(saveCi.getShippingCompany(), is(equalTo(ci.getShippingCompany()))),
                () -> assertThat(saveCi.getShippingAddress1(), is(equalTo(ci.getShippingAddress1()))),
                () -> assertThat(saveCi.getShippingAddress2(), is(equalTo(ci.getShippingAddress2()))),
                () -> assertThat(saveCi.getShippingCity(), is(equalTo(ci.getShippingCity()))),
                () -> assertThat(saveCi.getShippingPostcode(), is(equalTo(ci.getShippingPostcode()))),
                () -> assertThat(saveCi.getShippingState(), is(equalTo(ci.getShippingState()))),
                () -> assertThat(saveCi.getShippingCountry(), is(equalTo(ci.getShippingCountry()))),
                () -> assertThat(saveCi.getShippingPhone(), is(equalTo(ci.getShippingPhone()))),
                () -> assertThat(saveCi.getShippingFax(), is(equalTo(ci.getShippingFax()))),
                () -> assertThat(saveCi.getToken(), is(equalTo(ci.getToken())))
        );
    }

    private CustomerInfo generateCustomerInfo() {
        return CustomerInfo.builder().customerNumber(4111111111111111L)
                .startMonth((short) 1)
                .startYear((short) 2017)
                .expiryMonth((short) 1)
                .expiryYear((short) 2020)
                .email("seckin@bumin.io")
                .billingTitle("Mr.")
                .billingFirstName("SECKIN")
                .billingLastName("SEN")
                .billingCompany("BUMIN")
                .billingAddress1("Billing_Address1")
                .billingAddress2("Billing_Address2")
                .billingCity("ANTALYA")
                .billingPostcode("07070")
                .billingState("ANTALYA")
                .billingCountry("TR")
                .billingPhone("05554443322")
                .billingFax("05554443322")
                .shippingTitle("Mr.")
                .shippingFirstName("SECKIN")
                .shippingLastName("SEN")
                .shippingCompany("BUMIN")
                .shippingAddress1("Billing_Address1")
                .shippingAddress2("Billing_Address2")
                .shippingCity("ANTALYA")
                .shippingPostcode("07070")
                .shippingState("ANTALYA")
                .shippingCountry("TR")
                .shippingPhone("05554443322")
                .shippingFax("05554443322")
                .issueNumber(123456L)
                .birthday(new Date())
                .gender(Gender.MALE)
                .token("1t2o3k4e5n")
                .build();
    }
}