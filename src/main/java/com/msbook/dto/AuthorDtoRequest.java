package com.msbook.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorDtoRequest(@NotBlank String name) {
}
