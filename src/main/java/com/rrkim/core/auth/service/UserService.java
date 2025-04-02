package com.rrkim.core.auth.service;

import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.Eula;
import com.rrkim.core.auth.domain.Role;
import com.rrkim.core.auth.domain.User;
import com.rrkim.core.auth.dto.data.UserDto;
import com.rrkim.core.auth.dto.request.CheckIdRequestDto;
import com.rrkim.core.auth.dto.request.SignUpRequestDto;
import com.rrkim.core.auth.dto.request.UpdateUserInfoRequestDto;
import com.rrkim.core.auth.repository.EulaRepository;
import com.rrkim.core.auth.repository.RoleRepository;
import com.rrkim.core.auth.repository.UserQRepository;
import com.rrkim.core.auth.repository.UserRepository;
import com.rrkim.core.auth.util.AuthUtility;
import com.rrkim.core.auth.vo.UserVO;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EulaRepository eulaRepository;
    private final UserQRepository userQRepository;

    @Transactional
    public UserDto signUpProcess(@Valid SignUpRequestDto signUpRequestDto) {
        String originPassword = signUpRequestDto.getPassword();
        String encodedPassword = passwordEncoder.encode(originPassword);

        String userId = signUpRequestDto.getUserId();
        User currentUser = userRepository.findByUserId(userId);
        if (currentUser != null) {
            throw new UnhandledExecutionException("auth.duplicateUserId");
        }

        User user = User.builder().userId(userId)
                .password(encodedPassword)
                .userNm(signUpRequestDto.getUserNm())
                .gender(signUpRequestDto.getGender())
                .birthDate(signUpRequestDto.getBirthDate())
                .useYn(true).build();

        List<String> agreedEulaIds = signUpRequestDto.getAgreedEulaIds();
        List<Eula> eulas = eulaRepository.findByRequireTrueOrEulaIdIn(agreedEulaIds);
        List<String> requireEulaIds = eulas.stream().filter(Eula::isRequireYn).map(Eula::getEulaId).toList();
        List<Eula> agreeEulas = eulas.stream().filter(d -> agreedEulaIds.contains(d.getEulaId())).toList();
        if (requireEulaIds.stream().anyMatch(d -> !agreedEulaIds.contains(d))) {
            throw new UnhandledExecutionException("auth.eulaAgreementRequired");
        }
        user.addEula(agreeEulas);

        List<RoleType> roleTypes = List.of(RoleType.USER);
        List<Role> roles = roleRepository.findAllByRoleTypeIn(roleTypes);
        user.addRole(roles);

        userRepository.save(user);
        return new UserDto(user);
    }

    public boolean checkIdProcess(@Valid CheckIdRequestDto checkIdRequestDto) {
        String userId = checkIdRequestDto.getUserId();
        User user = userRepository.findByUserId(userId);

        return user == null;
    }

    public UserDto getUserInfo() {
        UserVO userVO = AuthUtility.getUserVO();
        if (userVO == null) {
            throw new UnhandledExecutionException("auth.forbidden");
        }

        String userId = userVO.getUserId();
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UnhandledExecutionException("auth.userNotFound");
        }

        System.out.println("user.getUserId() = " + user.getUserId());
        return userQRepository.findByUserId(userId);
    }

    @Transactional
    public UserDto updateUserInfo(UpdateUserInfoRequestDto updateUserInfoRequestDto) {
        String userId = updateUserInfoRequestDto.getUserId();
        UserVO userVO = AuthUtility.getUserVO();
        if (userVO == null) {
            throw new UnhandledExecutionException("auth.unauthorized");
        } else if (!userVO.getUserId().equals(userId)) {
            throw new UnhandledExecutionException("auth.modifyForbidden");
        }

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UnhandledExecutionException("auth.userNotFound");
        }

        // 비밀번호 변경 처리
        updatePassword(user, updateUserInfoRequestDto.getOriginPassword(), updateUserInfoRequestDto.getNewPassword());

        // 유저 정보 업데이트
        Optional.ofNullable(updateUserInfoRequestDto.getUserNm())
                .filter(userNm -> !userNm.isEmpty())
                .ifPresent(user::setUserNm);

        Optional.ofNullable(updateUserInfoRequestDto.getGender())
                .ifPresent(user::setGender);

        Optional.ofNullable(updateUserInfoRequestDto.getBirthDate())
                .ifPresent(user::setBirthDate);

        return getUserInfo();
    }

    private void updatePassword(User user, String originPassword, String newPassword) {
        if (isValidPasswordUpdate(originPassword, newPassword)) {
            if (!passwordEncoder.matches(originPassword, user.getPassword())) {
                throw new UnhandledExecutionException("auth.passwordInvalid");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
        }
    }

    private boolean isValidPasswordUpdate(String originPassword, String newPassword) {
        return originPassword != null && !originPassword.isEmpty()
                && newPassword != null && !newPassword.isEmpty();
    }

}
