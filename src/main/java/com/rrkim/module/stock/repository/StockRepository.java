package com.rrkim.module.stock.repository;

import com.rrkim.module.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long>, StockQueryRepositoryCustom {
    Optional<Stock> findByTicker(String ticker);
}
