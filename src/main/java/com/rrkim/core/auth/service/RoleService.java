package com.rrkim.core.auth.service;

import com.rrkim.core.auth.domain.Role;
import com.rrkim.core.auth.dto.request.CreateRoleRequestDto;
import com.rrkim.core.auth.dto.data.RoleDto;
import com.rrkim.core.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleDto createRole(CreateRoleRequestDto createRoleRequestDto) {
        Role role = Role.builder().roleType(createRoleRequestDto.getRoleId())
                                  .roleNm(createRoleRequestDto.getRoleNm())
                                  .useYn(true).build();

        roleRepository.save(role);
        return new RoleDto(role);
    }
}
