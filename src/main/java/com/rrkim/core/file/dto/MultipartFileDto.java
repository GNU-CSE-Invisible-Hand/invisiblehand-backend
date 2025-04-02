package com.rrkim.core.file.dto;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MultipartFileDto {
    private MultipartFile multipartFile;

    private final String fileName;

    private final String extension;

    private final long size;

    public MultipartFileDto(MultipartFile multipartFile) {
        String extension;
        this.fileName = multipartFile.getOriginalFilename();
        extension = FilenameUtils.getExtension(this.fileName);
        if(extension == null || extension.isBlank()) { extension = null; }
        this.extension = extension;
        this.size = multipartFile.getSize();
    }
}
