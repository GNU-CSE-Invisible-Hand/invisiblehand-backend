package com.rrkim.core.policy.config;

import com.rrkim.core.auth.constant.Gender;
import com.rrkim.core.auth.constant.RoleType;
import com.rrkim.core.auth.domain.Eula;
import com.rrkim.core.auth.dto.request.CreateRoleRequestDto;
import com.rrkim.core.auth.dto.request.SignUpRequestDto;
import com.rrkim.core.auth.repository.EulaRepository;
import com.rrkim.core.auth.service.EulaService;
import com.rrkim.core.auth.service.RoleService;
import com.rrkim.core.auth.service.UserService;
import com.rrkim.core.policy.constant.PolicyType;
import com.rrkim.core.policy.domain.Policy;
import com.rrkim.core.policy.dto.request.SetPolicyRequestDto;
import com.rrkim.core.policy.repository.PolicyRepository;
import com.rrkim.core.policy.service.PolicyService;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.service.MessageSourceService;
import com.rrkim.core.common.util.DateUtility;
import com.rrkim.core.file.util.FileUtility;
import com.rrkim.core.common.util.StringUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupConfiguration {

    private final PolicyService policyService;
    private final RoleService roleService;
    private final UserService userService;
    private final PolicyRepository policyRepository;
    private final MessageSourceService messageSourceService;
    private final EulaRepository eulaRepository;

    @Value("${framework-settings.setup.admin-user-id:}")
    private String adminUserId;

    @Value("${framework-settings.setup.admin-user-nm:}")
    private String adminUserNm;

    @PostConstruct
    @Transactional
    public synchronized void setup() {
        Policy initializedPolicy = policyRepository.findByPolicyId(PolicyType.INITIALIZED_DT.name());
        if (initializedPolicy != null) { return; }
        else if(adminUserId == null || adminUserId.isEmpty() || adminUserNm == null || adminUserNm.isEmpty()) {
            throw new UnhandledExecutionException(messageSourceService, "policy.adminInfoIsNotDefined");
        }

        SetPolicyRequestDto setPolicyRequestDto = SetPolicyRequestDto.builder().policyId(PolicyType.INITIALIZED_DT.name())
                                                                               .policyValue(DateUtility.getCurrentDate(DateUtility.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)).build();
        policyService.setPolicy(setPolicyRequestDto);

        for(RoleType roleType : RoleType.values()) {
            CreateRoleRequestDto createRoleDto = CreateRoleRequestDto.builder()
                    .roleId(roleType)
                    .roleNm(roleType.getRoleNm()).build();
            roleService.createRole(createRoleDto);
        }

        List<Eula> eulaByRequireYn = eulaRepository.findEulaByRequireYn(true);
        List<String> eulaIds = eulaByRequireYn.stream().map(Eula::getEulaId).toList();
        String userPassword = StringUtility.getRandomString(100);
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .userId(adminUserId)
                .userNm(adminUserNm)
                .password(userPassword)
                .gender(Gender.NONE)
                .birthDate(LocalDate.of(1990, 1, 1 ))
                .agreedEulaIds(eulaIds).build();
        userService.signUpProcess(signUpRequestDto);

        String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir, "init-password.txt");
        FileUtility.createTextFile(file, userPassword);
    }
}
