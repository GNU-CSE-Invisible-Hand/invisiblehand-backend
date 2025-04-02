package com.rrkim.module.news.repository;

import com.rrkim.module.news.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TagRepository")
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByTagId(String tagId);
    List<Tag> findTagByTagIdIn(List<String> tagIds);

}
