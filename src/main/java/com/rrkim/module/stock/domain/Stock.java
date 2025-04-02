package com.rrkim.module.stock.domain;

import com.rrkim.core.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stock")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String ticker;

    private String companyName;
    private BigDecimal currentPrice;
    private BigDecimal previousClosePrice;
    private BigDecimal high52week;
    private BigDecimal low52week;
    private String buyIndex;
}
