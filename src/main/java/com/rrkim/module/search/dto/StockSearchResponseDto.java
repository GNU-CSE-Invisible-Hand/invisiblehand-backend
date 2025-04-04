package com.rrkim.module.search.dto;

import com.rrkim.module.stock.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockSearchResponseDto {
    private String ticker;
    private String companyName;
    private BigDecimal currentPrice;
    private BigDecimal previousClosePrice;
    private String buyIndex;

    public StockSearchResponseDto(Stock stock){
        this.ticker = stock.getTicker();
        this.companyName = stock.getCompanyName();
        this.currentPrice = stock.getCurrentPrice();
        this.previousClosePrice = stock.getPreviousClosePrice();
        this.buyIndex = stock.getBuyIndex();
    }
}
