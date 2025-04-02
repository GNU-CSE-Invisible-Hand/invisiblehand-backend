package com.rrkim.module.news.repository;

import com.rrkim.module.news.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("QuizRepository")
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
