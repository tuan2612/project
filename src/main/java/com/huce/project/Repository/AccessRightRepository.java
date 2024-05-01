package com.huce.project.Repository;
import com.huce.project.Entity.AccessRightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AccessRightRepository extends JpaRepository<AccessRightEntity, Long> {
}