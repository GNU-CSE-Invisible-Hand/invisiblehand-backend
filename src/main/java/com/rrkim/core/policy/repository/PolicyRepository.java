package com.rrkim.core.policy.repository;

import com.rrkim.core.policy.domain.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PolicyRepository")
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Policy findByPolicyId(String policyId);
}
