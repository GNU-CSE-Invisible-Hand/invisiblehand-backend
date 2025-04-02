package com.rrkim.module.myinfo.repository;

import com.rrkim.module.myinfo.domain.UserReadArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserReadArticleRepository extends JpaRepository<UserReadArticle, Long> {
    List<UserReadArticle> findAllByUser_Id(Long userIdx);
    List<UserReadArticle> findAllByUser_IdAndReadTimeBetween(Long userIdx, LocalDateTime startTime, LocalDateTime endTime);
}

