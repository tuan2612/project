package com.huce.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huce.project.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String userName);

}
