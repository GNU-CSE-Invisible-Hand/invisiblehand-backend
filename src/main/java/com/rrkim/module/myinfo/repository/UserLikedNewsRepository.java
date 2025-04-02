package com.rrkim.module.myinfo.repository;

import com.rrkim.core.auth.domain.User;
import com.rrkim.module.myinfo.domain.UserLikedNews;
import com.rrkim.module.news.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikedNewsRepository extends JpaRepository<UserLikedNews, Long> {
    List<UserLikedNews> findAllByUser_Id(Long userId);

    // 특정 아티클의 좋아요 여부 확인
    Optional<UserLikedNews> findByUser_UserIdAndArticle_Id(String userId, Long articleIdx);

    Optional<UserLikedNews> findByUserAndArticle(User user, Article article);
}

