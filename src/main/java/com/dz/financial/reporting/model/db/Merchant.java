package com.dz.financial.reporting.model.db;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "MERCHANT")
public class Merchant {
    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    List<Transaction> transactions;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean allowPartialRefund;
    private boolean allowPartialCapture;

}
