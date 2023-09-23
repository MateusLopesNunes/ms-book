package com.msbook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthDtoRequest(@NotBlank @Email String email, @NotBlank String password) {
}
