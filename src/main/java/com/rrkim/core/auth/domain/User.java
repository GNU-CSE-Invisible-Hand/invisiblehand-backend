package com.rrkim.core.auth.domain;

import com.rrkim.core.auth.constant.Gender;
import com.rrkim.core.common.domain.BaseEntity;
import com.rrkim.module.myinfo.domain.UserInterestTag;
import com.rrkim.module.news.domain.Tag;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class User extends BaseEntity {

    @Column(name = "user_id", nullable = false, length = 100, unique = true)
    @Email
    private String userId;

    @Setter
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Setter
    @Column(name = "user_nm", nullable = false, length = 30)
    private String userNm;

    @Column(name = "use_yn", nullable = false, columnDefinition = BaseEntity.USE_YN_ENUM)
    private boolean useYn;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Setter
    @NotNull
    @Column(name = "birth_date", nullable = false)
    LocalDate birthDate;

    @NotNull
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserInterestTag> userInterestTags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<UserEula> userEulas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRole> userRoles = new ArrayList<>();

    public void addRole(List<Role> roles) {
        for (Role role : roles) {
            UserRole userRole = UserRole.builder().user(this).role(role).build();
            this.userRoles.add(userRole);
        }
    }

    public void addInterestTag(List<Tag> tags) {
        for (Tag tag : tags) {
            UserInterestTag userInterestNewsTag = UserInterestTag.builder().user(this).tag(tag).build();
            this.userInterestTags.add(userInterestNewsTag);
        }
    }

    public void updateInterestTags(List<Tag> newInterestTags) {
        List<UserInterestTag> existingTags = this.getUserInterestTags();
        Set<Long> newTagIds = newInterestTags.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());

        List<UserInterestTag> tagsToRemove = existingTags.stream()
                .filter(existingTag -> !newTagIds.contains(existingTag.getTag().getId()))
                .toList();

        Set<Long> existingTagIds = existingTags.stream()
                .map(tag -> tag.getTag().getId())
                .collect(Collectors.toSet());

        List<Tag> tagsToAdd = newInterestTags.stream()
                .filter(newTag -> !existingTagIds.contains(newTag.getId()))
                .toList();

        tagsToRemove.forEach(existingTags::remove);
        this.addInterestTag(tagsToAdd);
    }


    public void addEula(List<Eula> eulas) {
        LocalDateTime now = LocalDateTime.now();
        for (Eula eula : eulas) {
            UserEula userEula = UserEula.builder().user(this).eula(eula).agreeDate(now).build();
            this.userEulas.add(userEula);
        }
    }
}
