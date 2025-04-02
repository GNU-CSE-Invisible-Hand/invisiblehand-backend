package com.rrkim.core.auth.repository;

import com.rrkim.core.auth.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRoleRepository")
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
