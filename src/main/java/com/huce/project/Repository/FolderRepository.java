package com.huce.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huce.project.entity.FolderEntity;
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    FolderEntity findByFoldername(String foldername);
}