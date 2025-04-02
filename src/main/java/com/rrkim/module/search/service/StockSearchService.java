package com.rrkim.module.search.service;

import com.rrkim.module.search.dto.StockSearchResponseDto;
import com.rrkim.module.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockSearchService {
    private final StockRepository stockRepository;

    public List<StockSearchResponseDto> searchByTicker(String ticker){
        return stockRepository.findByTicker(ticker)
                .stream()
                .map(StockSearchResponseDto::new)
                .collect(Collectors.toList());
    }
}
