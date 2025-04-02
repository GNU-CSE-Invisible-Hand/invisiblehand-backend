package com.rrkim.module.news.repository;

import com.rrkim.module.news.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JournalRepository")
public interface JournalRepository extends JpaRepository<Journal, Long> {

    Journal findByJournalId(String journalId);
}
