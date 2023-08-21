package com.example.thejobs.dto.consultant;

import com.example.thejobs.dto.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ConsultantDTO {
    private String id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String jobType;

    @NotNull
    @NotEmpty
    @NotBlank
    private String firstName;

    private String lastName;
    @Email
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered ")

    @NotNull
    @NotEmpty
    @NotBlank
    private String mobile;

    @NotNull
    @NotEmpty
    @NotBlank
    private String nic;

    @NotNull
    @NotEmpty
    @NotBlank
    private String country;

    @Min(8)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private List<TimeSlots> timeSlots;

    private boolean status;

    private Date createdDate;
}
