package com.rrkim.core.common.domain;

import com.rrkim.core.common.util.RequestUtility;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Identifiable {

    public static final String USE_YN_ENUM = "enum('Y','N')";
    public static final String TEXT = "TEXT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name="reg_dt", updatable = false)
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column(name="mod_dt")
    private LocalDateTime modDt;

    @CreatedBy
    @Column(name="reg_idx", updatable = false)
    private Long regIdx;

    @LastModifiedBy
    @Column(name="mod_idx", updatable = false)
    private Long modIdx;

    @Column(name = "reg_ip", updatable = false)
    private String regIp;

    @Column(name = "mod_ip")
    private String modIp;

    @PrePersist
    public void prePersist() {
        this.regIp = this.modIp = RequestUtility.getClientIpFromRequest();
    }

    @PreUpdate
    public void preUpdate() {
        this.modIp = RequestUtility.getClientIpFromRequest();
    }
}
