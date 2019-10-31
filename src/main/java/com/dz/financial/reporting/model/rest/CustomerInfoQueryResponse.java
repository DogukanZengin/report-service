package com.dz.financial.reporting.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoQueryResponse {
    @JsonProperty("customerInfo")
    private CustomerInfoJson customerInfo;
}