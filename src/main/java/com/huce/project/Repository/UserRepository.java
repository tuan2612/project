package com.huce.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huce.project.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String userName);

}
