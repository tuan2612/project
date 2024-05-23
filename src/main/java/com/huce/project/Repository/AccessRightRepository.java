package com.huce.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huce.project.entity.AccessRightEntity;
@Repository
public interface AccessRightRepository extends JpaRepository<AccessRightEntity, Long> {
}