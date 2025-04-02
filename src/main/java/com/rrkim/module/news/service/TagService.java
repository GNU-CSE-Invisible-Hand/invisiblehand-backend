package com.rrkim.module.news.service;

import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.ObjectUtility;
import com.rrkim.module.news.domain.Journal;
import com.rrkim.module.news.domain.News;
import com.rrkim.module.news.domain.Tag;
import com.rrkim.module.news.dto.data.NewsDto;
import com.rrkim.module.news.dto.data.TagDto;
import com.rrkim.module.news.dto.request.CreateNewsRequestDto;
import com.rrkim.module.news.dto.request.CreateTagRequestDto;
import com.rrkim.module.news.repository.JournalRepository;
import com.rrkim.module.news.repository.NewsRepository;
import com.rrkim.module.news.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public List<TagDto> selectTagList() {
        return tagRepository.findAll().stream().map(TagDto::new).toList();
    }

    @Transactional
    public TagDto createTag(@Valid CreateTagRequestDto createTagRequestDto) {
        String tagId = createTagRequestDto.getTagId();
        String tagName = createTagRequestDto.getTagName();

        Tag findTag = tagRepository.findTagByTagId(tagId);
        if(findTag != null) {
            throw new UnhandledExecutionException("news.alreadyTagExists");
        }

        Tag tag = Tag.builder().tagName(tagName).tagId(tagId).build();
        tagRepository.save(tag);

        return new TagDto(tag);
    }
}
