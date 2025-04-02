package com.rrkim.core.auth.repository;

import com.rrkim.core.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    @Query("SELECT u FROM User u join fetch u.userRoles ur join fetch ur.role r where u.userId = :userId")
    User findByUserIdWithUserRolesUsingFetchJoin(@Param("userId") String userId);
}
