package com.rrkim.core.auth.domain;

import lombok.*;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Embeddable
@Access(AccessType.FIELD)
public class Email {

    @jakarta.validation.constraints.Email
    @Column(nullable = false, columnDefinition = "TEXT")
    private String email;

    public String getHost() {
        return email.substring(email.indexOf("@"));
    }

    public String getId() {
        return email.substring(0, email.indexOf("@"));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
