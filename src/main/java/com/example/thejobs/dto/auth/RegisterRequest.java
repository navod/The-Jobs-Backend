package com.example.thejobs.dto.auth;

import com.example.thejobs.dto.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String id;
    private String email;
    private String password;
    private Role role;
}
