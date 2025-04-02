package com.rrkim.core.file.dto;

import com.rrkim.core.file.domain.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FileDto {

    public FileDto(File file) {
        this.id = file.getId();
        this.savePath = file.getSavePath();
        this.saveFileNm = file.getSaveFileNm();
        this.originFileNm = file.getOriginFileNm();
    }

    private Long id;

    private String savePath;

    private String saveFileNm;

    private String originFileNm;
}
