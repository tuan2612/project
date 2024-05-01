package com.huce.project.Repository;
import com.huce.project.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
     List<FileEntity> findByFilename(String filename);
}
