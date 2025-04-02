package com.rrkim.core.auth.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.dto.data.UserDto;
import com.rrkim.core.common.repository.AbstractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rrkim.core.auth.domain.QEula.eula;
import static com.rrkim.core.auth.domain.QRole.role;
import static com.rrkim.core.auth.domain.QUser.user;
import static com.rrkim.core.auth.domain.QUserEula.userEula;
import static com.rrkim.core.auth.domain.QUserRole.userRole;

@RequiredArgsConstructor
@Repository
public class UserQRepository extends AbstractRepository<User> {

    private final JPAQueryFactory queryFactory;

    public UserDto findByUserId(String userId) {
        Map<Long, UserDto> resultMap = queryFactory
                .select(user)
                .from(user)
                .leftJoin(user.userRoles, userRole)
                .leftJoin(userRole.role, role)
                .leftJoin(user.userEulas, userEula)
                .leftJoin(userEula.eula, eula)
                .where(
                    eqUserId(userId)
                ).transform(GroupBy.groupBy(user.id).as(
                        Projections.bean(UserDto.class,
                                user.id.as("userIdx"),
                                user.userId,
                                user.password,
                                user.userNm,
                                user.gender,
                                user.birthDate,
                                user.useYn
                        )
                ));

        System.out.println("resultMap = " + resultMap);
        resultMap.replaceAll((id, userDto) -> {
            if (userDto.getUserInterestTags() != null) {
                // 중복 제거 및 빈 문자열 필터링
                List<String> filteredTags = userDto.getUserInterestTags().stream()
                        .filter(tag -> tag != null && !tag.trim().isEmpty()) // 빈 문자열 및 null 제거
                        .distinct() // 중복 제거
                        .collect(Collectors.toList());

                userDto.setUserInterestTags(filteredTags);
            }
            return userDto;
        });

        return resultMap.values().stream().findFirst().orElse(null);
    }

    private BooleanExpression eqUserId(String userId) {
        if(userId == null) { return null; }
        return user.userId.eq(userId);
    }
}
