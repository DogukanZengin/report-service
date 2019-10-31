package com.dz.financial.reporting.model.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.dz.financial.reporting.model.db.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"customerNumber", "created_at", "updated_at", "deleted_at", "expiryMonth",
        "expiryYear", "startMonth", "startYear", "issueNumber", "email", "birthday", "gender",
        "billingTitle", "billingFirstName", "billingLastName", "billingCompany", "billingAddress1",
        "billingAddress2", "billingCity", "billingPostcode", "billingState", "billingCountry",
        "billingPhone", "billingFax", "shippingTitle", "shippingFirstName", "shippingLastName",
        "shippingCompany", "shippingAddress1", "shippingAddress2", "shippingCity", "shippingPostcode",
        "shippingState", "shippingCountry", "shippingPhone", "shippingFax", "token"})
public class CustomerInfoJson {
    @JsonProperty("customerNumber")
    private Long number;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("updated_at")
    private Date modifiedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("deleted_at")
    private Date deletedAt;
    @JsonProperty("expiryMonth")
    private Short expiryMonth;
    @JsonProperty("expiryYear")
    private Short expiryYear;
    @JsonProperty("startMonth")
    private Short startMonth;
    @JsonProperty("startYear")
    private Short startYear;
    @JsonProperty("issueNumber")
    private Long issueNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    @JsonProperty("gender")
    private Gender gender;
    @JsonProperty("billingTitle")
    private String billingTitle;
    @JsonProperty("billingFirstName")
    private String billingFirstName;
    @JsonProperty("billingLastName")
    private String billingLastName;
    @JsonProperty("billingCompany")
    private String billingCompany;
    @JsonProperty("billingAddress1")
    private String billingAddress1;
    @JsonProperty("billingAddress2")
    private String billingAddress2;
    @JsonProperty("billingCity")
    private String billingCity;
    @JsonProperty("billingPostcode")
    private String billingPostcode;
    @JsonProperty("billingState")
    private String billingState;
    @JsonProperty("billingCountry")
    private String billingCountry;
    @JsonProperty("billingPhone")
    private String billingPhone;
    @JsonProperty("billingFax")
    private String billingFax;
    @JsonProperty("shippingTitle")
    private String shippingTitle;
    @JsonProperty("shippingFirstName")
    private String shippingFirstName;
    @JsonProperty("shippingLastName")
    private String shippingLastName;
    @JsonProperty("shippingCompany")
    private String shippingCompany;
    @JsonProperty("shippingAddress1")
    private String shippingAddress1;
    @JsonProperty("shippingAddress2")
    private String shippingAddress2;
    @JsonProperty("shippingCity")
    private String shippingCity;
    @JsonProperty("shippingPostcode")
    private String shippingPostcode;
    @JsonProperty("shippingState")
    private String shippingState;
    @JsonProperty("shippingCountry")
    private String shippingCountry;
    @JsonProperty("shippingPhone")
    private String shippingPhone;
    @JsonProperty("shippingFax")
    private String shippingFax;
    @JsonProperty("token")
    private String token;
}
