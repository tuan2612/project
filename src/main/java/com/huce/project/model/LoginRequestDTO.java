package com.huce.project.model;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    private String username;
    private String password;
}