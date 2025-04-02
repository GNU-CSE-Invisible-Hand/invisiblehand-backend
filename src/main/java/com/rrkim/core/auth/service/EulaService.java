package com.rrkim.core.auth.service;

import com.rrkim.core.auth.domain.Eula;
import com.rrkim.core.auth.domain.Role;
import com.rrkim.core.auth.dto.data.EulaDto;
import com.rrkim.core.auth.dto.data.RoleDto;
import com.rrkim.core.auth.dto.request.CreateRoleRequestDto;
import com.rrkim.core.auth.repository.EulaRepository;
import com.rrkim.core.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EulaService {

    private final EulaRepository eulaRepository;

    @Transactional
    public List<EulaDto> selectEulaList() {
        List<Eula> eulas = eulaRepository.findAll();
        return eulas.stream().map(EulaDto::new).toList();
    }
}
