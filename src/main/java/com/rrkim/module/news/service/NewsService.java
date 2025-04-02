package com.rrkim.module.news.service;

import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.util.AuthUtility;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.core.file.domain.File;
import com.rrkim.core.file.dto.FileDto;
import com.rrkim.core.file.dto.MultipartFileDto;
import com.rrkim.core.file.service.FileService;
import com.rrkim.core.file.util.FileUtility;
import com.rrkim.module.news.domain.News;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.request.CreateNewsRequestDto;
import com.rrkim.module.news.dto.request.DeleteNewsRequestDto;
import com.rrkim.module.news.dto.request.SelectNewsRequestDto;
import com.rrkim.module.news.repository.NewsRepository;
import com.rrkim.module.news.repository.NewsQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.Tag;
import com.rrkim.module.news.repository.JournalRepository;
import com.rrkim.module.news.repository.TagRepository;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsQRepository newsQRepository;
    private final JournalRepository journalRepository;
    private final TagRepository tagRepository;
    private final FileService fileService;


    public List<NewsDto> selectNewsList(SelectNewsRequestDto selectNewsRequestDto) {
        return newsQRepository.find(selectNewsRequestDto);
    }

    @Transactional
    public NewsDto createNews(CreateNewsRequestDto createNewsRequestDto) throws IOException {
        String journalId = createNewsRequestDto.getJournalId();
        List<String> tagIds = createNewsRequestDto.getTagIds();
        Journal journal = journalRepository.findByJournalId(journalId);
        if(journal == null) { throw new UnhandledExecutionException("news.journalNotFound"); }

        News news = News.builder()
                .title(createNewsRequestDto.getTitle())
                .link(createNewsRequestDto.getLink())
                .journal(journal)
                .publicationDate(createNewsRequestDto.getPublicationDate())
                .build();

        if (tagIds != null && !tagIds.isEmpty()) {
            List<Tag> tags = tagRepository.findTagByTagIdIn(tagIds);
            if(!ObjectUtility.isIdsMatchObjects(tagIds, tags, Tag::getTagId)) {
                throw new UnhandledExecutionException("news.tagNotFound");
            }
            news.addNewsTag(tags);
        }

        try {
            String photoLink = createNewsRequestDto.getPhotoLink();
            if(photoLink != null && !photoLink.isEmpty()) {
                File file = fileService.downloadFile(photoLink);
                news.setNewsPhotoFile(file);
            }
        } catch (IOException e) {
            throw new UnhandledExecutionException("news.uploadFileError");
        }

        newsRepository.save(news);
        return new NewsDto(news);
    }

    @Transactional
    public List<NewsDto> deleteNews(DeleteNewsRequestDto deleteNewsRequestDto) {
        List<RoleType> userRoleList = AuthUtility.getUserRole();
        if(userRoleList == null || !userRoleList.contains(RoleType.ADMIN)) {
            throw new UnhandledExecutionException("auth.forbidden");
        }

        List<Long> newsIdxes = deleteNewsRequestDto.getNewsIdxes();
        List<News> newsList = newsRepository.findNewsByIdIn(newsIdxes);
        List<NewsDto> newsDtoList = newsList.stream().map(NewsDto::new).toList();

        newsRepository.deleteAll(newsList);
        return newsDtoList;
    }
}
