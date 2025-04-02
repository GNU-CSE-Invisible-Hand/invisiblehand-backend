package com.rrkim.core.file.dto;

import com.rrkim.core.file.domain.File;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponseDto {
    private Long id;
    private String savePath;
    private String saveFileNm;
    private String originFileNm;

    public FileResponseDto(File file){
        this.id = file.getId();
        this.savePath = file.getSavePath();
        this.saveFileNm = file.getSaveFileNm();
        this.originFileNm = file.getOriginFileNm();
    }
}
