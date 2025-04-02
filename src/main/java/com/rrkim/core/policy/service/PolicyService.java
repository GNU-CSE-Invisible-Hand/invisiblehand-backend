package com.rrkim.core.policy.service;

import com.rrkim.core.policy.domain.Policy;
import com.rrkim.core.policy.dto.request.SetPolicyRequestDto;
import com.rrkim.core.policy.repository.PolicyRepository;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {

    private final PolicyRepository policyRepository;

    @Transactional
    public void setPolicy(SetPolicyRequestDto setPolicyRequestDto) {
        Policy findPolicy = policyRepository.findByPolicyId(setPolicyRequestDto.getPolicyId());
        if (findPolicy != null) { throw new UnhandledExecutionException("policy.alreadyPolicySet"); }

        Policy policy = Policy.builder().policyId(setPolicyRequestDto.getPolicyId())
                .policyValue(setPolicyRequestDto.getPolicyValue()).build();

        policyRepository.save(policy);
    }

    @Transactional
    public void updatePolicy(SetPolicyRequestDto setPolicyRequestDto) {
        Policy policy = policyRepository.findByPolicyId(setPolicyRequestDto.getPolicyId());
        if (policy == null) { throw new UnhandledExecutionException("policy.policyNotFound"); }

        policy.changePolicyValue(setPolicyRequestDto.getPolicyValue());
    }
}
