package com.rrkim.core.policy.constant;

import lombok.Getter;

@Getter
public enum PolicyType {
    INITIALIZED_DT("INITIALIZED_DT");

    private final String configId;

    PolicyType(String configId) {
        this.configId = configId;
    }
}
