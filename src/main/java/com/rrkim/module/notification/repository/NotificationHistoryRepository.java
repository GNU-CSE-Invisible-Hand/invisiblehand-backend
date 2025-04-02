package com.rrkim.module.notification.repository;

import com.rrkim.module.notification.domain.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    Optional<NotificationHistory> findNotificationHistoryById(Long notificationHistoryIdx);

}
