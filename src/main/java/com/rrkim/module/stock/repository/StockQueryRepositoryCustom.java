package com.rrkim.module.stock.repository;

import com.rrkim.module.stock.domain.Stock;

import java.util.List;

public interface StockQueryRepositoryCustom {
    List<Stock> searchByTickerOrCompany(String keyword);
}
