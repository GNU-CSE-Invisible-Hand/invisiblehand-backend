package com.rrkim.module.stock.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockSaveRequestDto {
    private String ticker;
    private String companyName;
    private BigDecimal currentPrice;
    private BigDecimal previousClosePrice;
    private BigDecimal high52week;
    private BigDecimal low52week;
    private String buyIndex;
}
