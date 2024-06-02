package com.huce.project.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huce.project.entity.AccessRightEntity;
import com.huce.project.entity.EnumAccessType;
@Repository
public interface AccessRightRepository extends JpaRepository<AccessRightEntity, Long> {
    List<AccessRightEntity> findByUserIdAndAccessType(UUID userId, EnumAccessType accessType);
}