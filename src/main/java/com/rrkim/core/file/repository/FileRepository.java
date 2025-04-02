package com.rrkim.core.file.repository;

import com.rrkim.core.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("FileRepository")
public interface FileRepository extends JpaRepository<File, Long> {
    File findByFileId(String fileId);
}

