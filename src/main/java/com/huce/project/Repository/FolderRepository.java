package com.huce.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huce.project.entity.FolderEntity;

import jakarta.transaction.Transactional;
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    FolderEntity findByFoldername(String foldername);
    FolderEntity findByFolderId(Long folderid);
    @Transactional
    void deleteByFolderId(Long folderid);
}