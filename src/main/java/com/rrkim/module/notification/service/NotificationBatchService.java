package com.rrkim.module.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.repository.UserRepository;
import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.core.common.util.HashMapCreator;
import com.rrkim.module.myinfo.domain.UserInterestTag;
import com.rrkim.module.myinfo.dto.UserInfoResponseDto;
import com.rrkim.module.myinfo.repository.UserInterestTagRepository;
import com.rrkim.module.myinfo.service.MyInfoService;
import com.rrkim.module.news.domain.Tag;
import com.rrkim.module.news.dto.data.ArticleDto;
import com.rrkim.module.news.dto.data.JournalDto;
import com.rrkim.module.news.dto.data.TagDto;
import com.rrkim.module.news.repository.ArticleQRepository;
import com.rrkim.module.notification.constant.NotificationTopic;
import com.rrkim.module.notification.dto.request.SendNotificationRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationBatchService {

    private static final Logger log = LoggerFactory.getLogger(NotificationBatchService.class);
    private final MyInfoService myInfoService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final ArticleQRepository articleQRepository;
    private final UserInterestTagRepository userInterestTagRepository;

    // 정기적으로 모든 사용자에게 알림 발송
    //@Scheduled(cron = "0 0 9 * * *")
    @Transactional
    public void sendHotIssueNotifications() throws FirebaseMessagingException, JsonProcessingException {
        List<ArticleDto> popularArticleDtos = articleQRepository.getPopularArticleDtos(0, 1);
        if(popularArticleDtos == null || popularArticleDtos.isEmpty()) {
            return;
        }

        ArticleDto articleDto = popularArticleDtos.get(0);
        String title = articleDto.getTitle();
        Long articleIdx = articleDto.getArticleIdx();
        Map<String, String> data = HashMapCreator.getStringStringBuilder().put("articleIdx", String.valueOf(articleIdx)).build();
        sendNotification(NotificationTopic.HOT_ISSUE.getTopic(), "이 시간 인기있는 뉴스를 추천드릴게요! 🧐", title, data);
    }

    //@Scheduled(cron = "0 0 13 * * *")
    @Transactional
    public void sendInterestIssueNotifications() throws FirebaseMessagingException, JsonProcessingException {
        List<UserInterestTag> userInterestTagList = userInterestTagRepository.findAll();
        if(userInterestTagList.isEmpty()) {
            return;
        }

        List<Tag> tags = userInterestTagList.stream().map(UserInterestTag::getTag).toList();
        List<Long> tagIdxes = tags.stream().map(BaseEntity::getId).toList();

        List<ArticleDto> interestArticleDtos = articleQRepository.getInterestArticleDtos(tagIdxes, 0, 1);
        if(interestArticleDtos == null || interestArticleDtos.isEmpty()) {
            return;
        }

        ArticleDto articleDto = interestArticleDtos.get(0);
        String title = articleDto.getTitle();
        Long articleIdx = articleDto.getArticleIdx();
        Map<String, String> data = HashMapCreator.getStringStringBuilder().put("articleIdx", String.valueOf(articleIdx)).build();
        sendNotification(NotificationTopic.HOT_ISSUE.getTopic(), "좋아할만한 뉴스를 추천드려요! 🧐", title, data);
    }

    private void sendNotification(String topic, String title, String content, Map<String, String> data) throws FirebaseMessagingException, JsonProcessingException {
        SendNotificationRequestDto sendNotificationRequestDto = SendNotificationRequestDto.builder()
                .topic(topic)
                .title(title)
                .content(content)
                .data(data)
                .build();

        notificationService.addNotificationQueue(sendNotificationRequestDto);
    }
}

