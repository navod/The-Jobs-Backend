package com.example.thejobs.dto;

import com.example.thejobs.dto.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainUserDTO {
    @Id
    private String id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String firstName;
    private String lastName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;
    @Column(nullable = false, unique = true)

    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
    
    @Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered ")
    @NotNull
    @NotEmpty
    @NotBlank
    private String mobile;
    @Column(nullable = false, unique = true)
    @NotNull
    @NotEmpty
    @NotBlank
    private String nic;
    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean status;
    private Date createdDate;
}