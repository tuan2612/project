package com.huce.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huce.project.entity.FileEntity;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
     List<FileEntity> findByFilename(String filename);
}
