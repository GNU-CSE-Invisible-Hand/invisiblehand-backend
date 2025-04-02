package com.rrkim.core.file.controller;

import com.rrkim.core.file.domain.File;
import com.rrkim.core.file.dto.FileResponseDto;
import com.rrkim.core.file.dto.MultipartFileDto;
import com.rrkim.core.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Tag(name = "파일 API")
@RestController
public class FileController {
    private final FileService fileService;

    @Operation(summary = "이미지 파일 조회", description = "이미지 파일을 조회합니다")
    @GetMapping("/file/image/{fileId}")
    public ResponseEntity<Resource> imageView(@PathVariable String fileId) throws IOException {
        return fileService.getImageView(fileId);
    }
}
