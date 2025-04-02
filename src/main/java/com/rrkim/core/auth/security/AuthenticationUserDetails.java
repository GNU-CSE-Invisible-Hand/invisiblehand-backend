package com.rrkim.core.auth.security;

import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.domain.UserRole;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class AuthenticationUserDetails implements UserDetails {

    private final User user;

    public AuthenticationUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isUseYn();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isUseYn();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isUseYn();
    }

    @Override
    public boolean isEnabled() {
        return user.isUseYn();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getUserRoles().stream().map(UserRole::getRole).map(d -> new SimpleGrantedAuthority(d.getRoleType().getRoleId())).collect(Collectors.toList());
    }
}
