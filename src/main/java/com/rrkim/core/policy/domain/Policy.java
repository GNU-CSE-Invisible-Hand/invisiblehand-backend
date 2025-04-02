package com.rrkim.core.policy.domain;

import com.rrkim.core.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class Policy extends BaseEntity {

    @Column(name = "policy_id", nullable = false, length = 30, unique = true)
    private String policyId;

    @Column(name = "policy_value", length = 100)
    private String policyValue;

    public void changePolicyValue(String policyValue) {
        this.policyValue = policyValue;
    }
}
