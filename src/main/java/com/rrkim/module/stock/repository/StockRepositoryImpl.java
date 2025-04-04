package com.rrkim.module.stock.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rrkim.module.stock.domain.QStock;
import com.rrkim.module.stock.domain.Stock;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockRepositoryImpl implements StockQueryRepositoryCustom{
    private final JPAQueryFactory query;

    public StockRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Stock> searchByTickerOrCompany(String keyword){
        QStock stock = QStock.stock;

        return query.selectFrom(stock)
                .where(
                        stock.ticker.containsIgnoreCase(keyword)
                                .or(stock.companyName.containsIgnoreCase(keyword))
                )
                .fetch();
    }
}
