package com.rrkim.module.news.repository;

import com.rrkim.module.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("NewsRepository")
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findNewsByIdIn(List<Long> newsIdxes);
}
