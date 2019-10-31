package com.dz.financial.reporting.model.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.dz.financial.reporting.model.db.enums.Operation;
import com.dz.financial.reporting.model.db.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"customerInfo", "fx", "transaction", "merchant"})
public class TransactionQueryResponse {
    @JsonProperty("customerInfo")
    private CustomerInfoJson customerInfo;

    @JsonProperty("fx")
    private Fx fx;

    @JsonProperty("transaction")
    private Transaction transaction;

    @JsonProperty("merchant")
    private Merchant merchant;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fx {
        @JsonProperty("merchant")
        private FxMerchant fxMerchant;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonPropertyOrder({"originalAmount", "originalCurrency"})
        public static class FxMerchant {
            private String originalAmount;
            private String originalCurrency;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transaction {
        @JsonProperty("merchant")
        private TransactionMerchant transactionMerchant;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonPropertyOrder({"id", "referenceNo", "merchantId", "fxTransactionId", "acquirerTransactionId",
                "chainId", "agentInfoId", "returnUrl", "status", "operation", "created_at", "updated_at",
                "deleted_at", "code", "message", "channel", "customData", "parentId", "type", "transactionId",
                "agent"})
        public static class TransactionMerchant {
            private Long id;
            private String referenceNo;
            private Long merchantId;
            private String fxTransactionId;
            private String acquirerTransactionId;
            private String chainId;
            private Long agentInfoId;
            private String returnUrl;
            private Status status;
            private Operation operation;
            @JsonProperty("created_at")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private Date createdAt;
            @JsonProperty("updated_at")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private Date modifiedAt;
            @JsonProperty("deleted_at")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private Date deletedAt;
            private String code;
            private String message;
            private String channel;
            private String customData;
            private Long parentId;
            private String type;
            private String transactionId;
            private Agent agent;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonPropertyOrder({"id", "customerIp", "customerUserAgent", "merchantIp", "merchantIp",
                    "merchantUserAgent", "created_at", "updated_at", "deleted_at"})
            public static class Agent {
                private Long id;
                private String customerIp;
                private String customerUserAgent;
                private String merchantIp;
                private String merchantUserAgent;
                @JsonProperty("created_at")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                private Date createdAt;
                @JsonProperty("updated_at")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                private Date modifiedAt;
                @JsonProperty("deleted_at")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                private Date deletedAt;
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Merchant {
        private String name;
    }
}