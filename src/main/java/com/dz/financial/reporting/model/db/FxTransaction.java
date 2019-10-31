package com.dz.financial.reporting.model.db;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "FX_TRANSACTION")
public class FxTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal originalAmount;
    private String originalCurrency;
    private BigDecimal convertedAmount;
    private String convertedCurrency;
    private String fxTransactionId;

    @OneToOne(mappedBy = "fxTransaction")
    private Transaction transaction;
}
