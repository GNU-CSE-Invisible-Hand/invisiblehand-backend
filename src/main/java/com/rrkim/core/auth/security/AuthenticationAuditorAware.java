package com.rrkim.core.auth.security;

import com.rrkim.core.auth.vo.UserVO;
import com.rrkim.core.auth.util.AuthUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        UserVO userVO = AuthUtility.getUserVO();
        if (userVO == null) { return Optional.empty(); }

        return Optional.ofNullable(userVO.getId());
    }
}
