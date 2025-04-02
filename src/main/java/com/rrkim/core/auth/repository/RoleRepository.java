package com.rrkim.core.auth.repository;

import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleId);
    List<Role> findAllByRoleTypeIn(List<RoleType> roleIds);
}
