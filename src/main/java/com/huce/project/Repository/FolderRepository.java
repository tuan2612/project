package com.huce.project.Repository;
import com.huce.project.Entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    FolderEntity findByFoldername(String foldername);
}