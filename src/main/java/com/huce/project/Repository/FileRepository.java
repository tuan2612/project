package com.huce.project.Repository;
import com.huce.project.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
