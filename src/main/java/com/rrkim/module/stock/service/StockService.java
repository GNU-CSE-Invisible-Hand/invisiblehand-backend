package com.rrkim.module.stock.service;

import com.rrkim.module.stock.domain.Stock;
import com.rrkim.module.stock.dto.StockSaveRequestDto;
import com.rrkim.module.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public void save(StockSaveRequestDto dto) {
        Stock stock = Stock.builder()
                .ticker(dto.getTicker())
                .companyName(dto.getCompanyName())
                .currentPrice(dto.getCurrentPrice())
                .previousClosePrice(dto.getPreviousClosePrice())
                .high52week(dto.getHigh52week())
                .low52week(dto.getLow52week())
                .buyIndex(dto.getBuyIndex())
                .build();

        stockRepository.save(stock);
    }

    public void deleteByTicker(String ticker) {
        stockRepository.findByTicker(ticker).ifPresent(stockRepository::delete);
    }
}
