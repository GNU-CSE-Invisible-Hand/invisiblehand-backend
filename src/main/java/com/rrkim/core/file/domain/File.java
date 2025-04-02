package com.rrkim.core.file.domain;


import com.rrkim.core.common.domain.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_id", nullable = false)
    private String fileId;

    @Column(name = "save_path", nullable = false)
    private String savePath;

    @Column(name = "save_file_nm", nullable = false, length = 50)
    private String saveFileNm;

    @Column(name = "origin_file_nm", nullable = false, length = 100)
    private String originFileNm;

    @Column(name = "use_yn", nullable = false, columnDefinition = BaseEntity.USE_YN_ENUM)
    private boolean useYn;
}
