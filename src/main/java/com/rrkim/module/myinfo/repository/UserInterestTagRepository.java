package com.rrkim.module.myinfo.repository;

import com.rrkim.module.myinfo.domain.UserInterestTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInterestTagRepository extends JpaRepository<UserInterestTag, Long> {

    List<UserInterestTag> findByUser_Id(Long userId);
}
