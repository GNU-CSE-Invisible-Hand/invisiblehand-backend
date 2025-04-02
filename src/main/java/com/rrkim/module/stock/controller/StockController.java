package com.rrkim.module.stock.controller;

import com.rrkim.module.stock.dto.StockSaveRequestDto;
import com.rrkim.module.stock.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
@Tag(name = "종목 저장/삭제 API")
public class StockController {
    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Void> saveStock(@RequestBody StockSaveRequestDto dto) {
        stockService.save(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ticker}")
    public ResponseEntity<Void> deleteStock(@PathVariable String ticker) {
        stockService.deleteByTicker(ticker);
        return ResponseEntity.noContent().build();
    }
}
