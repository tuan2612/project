package com.huce.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huce.project.entity.FileEntity;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
     List<FileEntity> findByFilename(String filename);
     List<FileEntity> findByFolderid(Long folderid);
     FileEntity findByFileId(Long fileId);
     FileEntity findByFilenameAndFolderid(String filename, Long folderid);
     @Transactional
    void deleteByFileId(long fileId);
}
