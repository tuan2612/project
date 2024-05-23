package com.huce.project.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;


import com.huce.project.convertor.RoleConvertor;

import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "Users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userID;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "pass_word", length = 50, nullable = false)
    private String password;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name="role",nullable = false)
    @Convert(converter = RoleConvertor.class)
    @Builder.Default
    private Role role = Role.USER; 

    @CreationTimestamp
    private Timestamp registrationDate;

}
