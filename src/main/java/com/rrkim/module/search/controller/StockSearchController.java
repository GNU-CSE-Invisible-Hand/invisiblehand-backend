package com.rrkim.module.search.controller;

import com.rrkim.module.search.dto.StockSearchResponseDto;
import com.rrkim.module.search.service.StockSearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "검색 API")
public class StockSearchController {
    private final StockSearchService stockSearchService;

    @GetMapping("/search")
    public ResponseEntity<List<StockSearchResponseDto>> searchStock(@RequestParam("q") String ticker) {
        List<StockSearchResponseDto> result = stockSearchService.searchByTicker(ticker);
        return ResponseEntity.ok(result);
    }
}
