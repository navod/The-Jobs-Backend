package com.example.thejobs.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerDTO {
    private String id;

    @NotBlank
    private String firstName;
    private String lastName;

    @Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered ")
    @NotNull
    @NotEmpty
    @NotBlank
    private String mobile;

    @NotNull
    @NotEmpty
    @NotBlank
    private String preferDestination;

    @Email
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    private String preferJobType;
    private String cv;


    private int age;

    private String description;
    private Date createdDate;
}
