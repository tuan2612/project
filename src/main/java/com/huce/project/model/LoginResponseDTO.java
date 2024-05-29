package com.huce.project.model;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String username;
}
