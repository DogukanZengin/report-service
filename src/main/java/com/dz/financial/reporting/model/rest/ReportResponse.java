package com.dz.financial.reporting.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.dz.financial.reporting.model.db.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"status", "data"})
public class ReportResponse {
    @JsonProperty("status")
    private Status status;

    @JsonProperty("data")
    private List<ReportData> reportData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonPropertyOrder({"count", "total", "currency"})
    public static class ReportData {
        @JsonProperty("count")
        private Long count;

        @JsonProperty("total")
        private String total;

        @JsonProperty("currency")
        private String currency;
    }
}
