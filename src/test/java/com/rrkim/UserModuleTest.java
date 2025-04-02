package com.rrkim;

import com.rrkim.core.auth.constant.Gender;
import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.Eula;
import com.rrkim.core.auth.domain.Role;
import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.domain.UserRole;
import com.rrkim.core.auth.dto.data.UserDto;
import com.rrkim.core.auth.dto.request.SignUpRequestDto;
import com.rrkim.core.auth.repository.EulaRepository;
import com.rrkim.core.auth.repository.UserRepository;
import com.rrkim.core.auth.service.UserService;
import com.rrkim.core.auth.util.AuthUtility;
import com.rrkim.core.common.util.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@SpringBootTest
public class UserModuleTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EulaRepository eulaRepository;

    @Test
    @DisplayName("회원가입")
    @Transactional
    public void join() {
        String userId = String.format("%s@%s.%s", StringUtility.getRandomString(8), StringUtility.getRandomString(5), StringUtility.getRandomString(3));
        String userPw = StringUtility.getRandomString(50);

        List<Eula> eulaByRequireYn = eulaRepository.findEulaByRequireYn(true);
        List<String> eulaIds = eulaByRequireYn.stream().map(Eula::getEulaId).toList();

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .userId(userId)
                .userNm(userId)
                .password(userPw)
                .gender(Gender.NONE)
                .birthDate(LocalDate.of(1990, 1, 1 ))
                .agreedEulaIds(eulaIds).build();
        UserDto userDto = userService.signUpProcess(signUpRequestDto);
        String createdUserId = userDto.getUserId();

        User findUser = userRepository.findByUserId(createdUserId);
        List<UserRole> userRoles = findUser.getUserRoles();
        String roleIds = AuthUtility.getUserRoleString(userDto.getUserRoles());

        log.info("=== userId: {}", findUser.getUserId());
        log.info("=== roleId: {}", roleIds);

        Assertions.assertEquals(userId, findUser.getUserId());
    }
}