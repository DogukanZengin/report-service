package com.dz.financial.reporting.model.db;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "AGENT")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerIp;
    private String customerUserAgent;
    private String merchantIp;
    private String merchantUserAgent;

    @Setter(AccessLevel.NONE)
    private Date createdAt;

    @Setter(AccessLevel.NONE)
    private Date modifiedAt;

    @Setter(AccessLevel.NONE)
    private Date deletedAt;

    @OneToOne(mappedBy = "agent")
    private Transaction transaction;

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
