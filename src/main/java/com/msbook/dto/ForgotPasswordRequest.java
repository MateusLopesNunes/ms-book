package com.msbook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ForgotPasswordRequest(@NotNull LocalDate birthDate, @Email @NotBlank String email) {
}