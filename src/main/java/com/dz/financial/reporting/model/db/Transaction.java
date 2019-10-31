package com.dz.financial.reporting.model.db;

import com.dz.financial.reporting.model.db.enums.Operation;
import com.dz.financial.reporting.model.db.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @Setter(AccessLevel.NONE)
    private String transactionId;

    @Setter(AccessLevel.NONE)
    private Date createdAt;

    @Setter(AccessLevel.NONE)
    private Date modifiedAt;

    @Setter(AccessLevel.NONE)
    private Date deletedAt;

    @Setter(AccessLevel.NONE)
    @Column(name = "TRANSACTION_DATE")
    private Long date;

    private String referenceNo;
    private String chainId;
    private String returnUrl;
    private Status status;
    private Operation operation;
    private String code;
    private String message;
    private String channel;
    private String customData;
    private String type;
    private boolean refundable;

    //TODO: skipping parentId, most probably transaction has a relationship on-self
    //private Transaction parent;
    //private List<Transaction> transactions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_INFO_ID")
    private CustomerInfo customerInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "AGENT_ID")
    private Agent agent;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FX_TRANSACTION_ID")
    private FxTransaction fxTransaction;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "IPN_ID")
    private IPN ipn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ACQUIRER_TRANSACTION_ID")
    private AcquirerTransaction acquirerTransaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MERCHANT_ID")
    private Merchant merchant;

    @PrePersist
    private void prePersist() {
        Date now = new Date();
        this.createdAt = now;
        this.modifiedAt = now;
        this.date = now.toInstant().getEpochSecond();
    }

    @PreUpdate
    private void preUpdate() {
        this.modifiedAt = new Date();
    }

    public String getTransactionId() {
        return id + "-" + date + "-" + merchant.getId();
    }
}
