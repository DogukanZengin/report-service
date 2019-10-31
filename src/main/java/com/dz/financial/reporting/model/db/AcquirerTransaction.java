package com.dz.financial.reporting.model.db;

import com.dz.financial.reporting.model.db.enums.AcquirerType;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "ACQUIRER_TRANSACTION")
public class AcquirerTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private AcquirerType type;
    private String acquirerTransactionId;

    @OneToOne(mappedBy = "acquirerTransaction")
    private Transaction transaction;
}
