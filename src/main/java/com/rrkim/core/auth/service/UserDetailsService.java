package com.rrkim.core.auth.service;

import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.domain.UserRole;
import com.rrkim.core.auth.repository.UserRepository;
import com.rrkim.core.auth.security.AuthenticationUserDetails;
import com.rrkim.core.auth.util.AuthUtility;
import com.rrkim.core.common.util.MessageUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        User user = userRepository.findByUserIdWithUserRolesUsingFetchJoin(username);

        if(user == null) {
            throw new UsernameNotFoundException(MessageUtility.getMessage("auth.userNotFound"));
        }

        List<UserRole> userRoles = user.getUserRoles();
        List<RoleType> roleTypes = new ArrayList<>();
        if(userRoles != null && !userRoles.isEmpty()) {
            roleTypes = new ArrayList<>(userRoles.stream().map(d -> d.getRole().getRoleType()).toList());
        }
        String roleIds = AuthUtility.getUserRoleString(roleTypes);
        log.info("=== Signing in user : {} - {}", user.getUserId(), roleIds);

        return new AuthenticationUserDetails(user);
    }
}
