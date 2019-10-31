package com.dz.financial.reporting.model.db;

import com.dz.financial.reporting.model.db.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "CUSTOMER_INFO")
public class CustomerInfo {
    @OneToMany(mappedBy = "customerInfo", cascade = CascadeType.ALL)
    List<Transaction> transactions;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter(AccessLevel.NONE)
    private Date createdAt;
    @Setter(AccessLevel.NONE)
    private Date modifiedAt;
    @Setter(AccessLevel.NONE)
    private Date deletedAt;
    private Long customerNumber;
    private Short expiryMonth;
    private Short expiryYear;
    private Short startMonth;
    private Short startYear;
    private Long issueNumber;
    private String email;
    private Date birthday;
    private Gender gender;
    private String billingTitle;
    private String billingFirstName;
    private String billingLastName;
    private String billingCompany;
    private String billingAddress1;
    private String billingAddress2;
    private String billingCity;
    private String billingPostcode;
    private String billingState;
    private String billingCountry;
    private String billingPhone;
    private String billingFax;
    private String shippingTitle;
    private String shippingFirstName;
    private String shippingLastName;
    private String shippingCompany;
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingCity;
    private String shippingPostcode;
    private String shippingState;
    private String shippingCountry;
    private String shippingPhone;
    private String shippingFax;
    private String token;

    @PrePersist
    private void prePersist() {
        Date now = new Date();
        this.createdAt = now;
        this.modifiedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        this.modifiedAt = new Date();
    }
}
