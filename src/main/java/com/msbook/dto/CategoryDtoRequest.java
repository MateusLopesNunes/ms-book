package com.msbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDtoRequest(@NotBlank String name) {
}
