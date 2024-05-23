package com.huce.project.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huce.project.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String userName);

}

