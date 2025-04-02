package com.rrkim.core.auth.util;

import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.Role;
import com.rrkim.core.auth.domain.UserRole;
import com.rrkim.core.auth.security.AuthenticationUserDetails;
import com.rrkim.core.auth.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUtility {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        return authentication.isAuthenticated();
    }

    public static AuthenticationUserDetails getUserDetailsVO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!isAuthenticated()) { return null; }

        return (AuthenticationUserDetails) authentication.getPrincipal();
    }

    public static UserVO getUserVO() {
        AuthenticationUserDetails authenticationUserDetails = getUserDetailsVO();
        if(authenticationUserDetails == null || authenticationUserDetails.getUser() == null) { return null; }

        return new UserVO(authenticationUserDetails.getUser());
    }

    public static List<RoleType> getUserRole() {
        AuthenticationUserDetails userDetailsVO = getUserDetailsVO();
        if(userDetailsVO == null) { return null; }

        Collection<? extends GrantedAuthority> authorities = userDetailsVO.getAuthorities();
        return authorities.stream().map(d -> RoleType.fromRoleId(d.getAuthority())).toList();
    }

    public static String getUserRoleString(List<RoleType> roleTypes) {
        if(roleTypes == null) { return null; }

        return roleTypes.stream().map(RoleType::getRoleId).collect(Collectors.joining(", "));
    }

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(BEARER_PREFIX)) { return null; }

        return bearerToken.substring(7);
    }
}
