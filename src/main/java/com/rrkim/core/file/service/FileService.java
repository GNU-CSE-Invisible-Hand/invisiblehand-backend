package com.rrkim.core.file.service;

import com.rrkim.core.file.dto.FileDto;
import com.rrkim.core.file.dto.MultipartFileDto;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.StringUtility;
import com.rrkim.core.file.domain.File;
import com.rrkim.core.file.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FileService {

    @Value("${framework-settings.file.save-path}")
    private String savePath;
    private final FileRepository fileRepository;

    @Transactional
    public File uploadFile(MultipartFileDto multipartFileDto) {
        MultipartFile multipartFile = multipartFileDto.getMultipartFile();
        String originFileNm = multipartFileDto.getFileName();
        String extension = multipartFileDto.getExtension();
        if (originFileNm == null || originFileNm.isBlank()) { throw new UnhandledExecutionException("auth.badFileName"); }

        originFileNm = StringUtility.normalizeString(originFileNm);
        String saveFileNm = generateFileName(extension);

        try {
            Path saveDirectory = Paths.get(savePath).toAbsolutePath().normalize();
            Path targetPath = saveDirectory.resolve(saveFileNm).normalize();
            Files.createDirectories(saveDirectory);

            if(Files.exists(targetPath)) { throw new UnhandledExecutionException("file.fileExists"); }
            multipartFile.transferTo(targetPath);
        } catch (IOException e) { throw new UnhandledExecutionException("file.uploadError"); }

        File file = File.builder().savePath(savePath)
                                        .originFileNm(originFileNm)
                                        .saveFileNm(saveFileNm).build();
        fileRepository.save(file);
        return file;
    }

    private String generateFileName(String extension) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return String.format("FILE_%s.%s", localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")), extension);
    }


    public File downloadFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = conn.getHeaderField("Content-Disposition");
            String originFileNm;

            if (disposition != null && disposition.contains("filename=\"")) {
                int index = disposition.indexOf("filename=\"") + 10;
                originFileNm = disposition.substring(index, disposition.indexOf("\"", index));
            } else {
                originFileNm = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                if (!originFileNm.isEmpty()) {
                    int questionMarkIndex = originFileNm.indexOf("?");
                    if (questionMarkIndex != -1) {
                        originFileNm = originFileNm.substring(0, questionMarkIndex);
                    }
                } else {
                    String contentType = conn.getContentType();
                    String extension = "";

                    if (contentType != null) {
                        extension = getExtensionFromContentType(contentType);
                    }

                    originFileNm = "downloaded_file" + (extension.isEmpty() ? "" : "." + extension);
                }
            }

            String extension = FilenameUtils.getExtension(originFileNm);
            if (originFileNm.isBlank()) { throw new UnhandledExecutionException("auth.badFileName"); }
            String saveFileNm = generateFileName(extension);
            Path saveDirectory = Paths.get(savePath).toAbsolutePath().normalize();
            Path targetPath = saveDirectory.resolve(saveFileNm).normalize();
            Files.createDirectories(saveDirectory);

            if(Files.exists(targetPath)) { throw new UnhandledExecutionException("file.fileExists"); }
            InputStream is = conn.getInputStream();
            Files.copy(is, targetPath);
            is.close();

            String fileId = StringUtility.createUUID();

            File file = File.builder()
                    .fileId(fileId)
                    .savePath(savePath)
                    .originFileNm(originFileNm)
                    .saveFileNm(saveFileNm).build();
            fileRepository.save(file);

            return file;
        } else {
            throw new IOException("파일을 다운로드하는데 실패했습니다.");
        }
    }

    public ResponseEntity<Resource> getImageView(String fileId) throws IOException {
        File file = fileRepository.findByFileId(fileId);
        String saveFilePath = file.getSavePath() + file.getSaveFileNm();
        String originFileName = file.getOriginFileNm();
        java.io.File imageFile = new java.io.File(saveFilePath);

        if (!imageFile.exists()) {
            throw new UnhandledExecutionException("file.fileNotFound");
        }

        // 파일의 콘텐츠 타입 결정
        String contentType = Files.probeContentType(imageFile.toPath());
        if (contentType == null) {
            throw new UnhandledExecutionException("file.badFile");
        }

        Resource resource = new FileSystemResource(imageFile);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + originFileName + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    private static String getExtensionFromContentType(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            default -> "";
        };
    }
}
