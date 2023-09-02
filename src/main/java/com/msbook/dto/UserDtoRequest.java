package com.msbook.dto;

import com.msbook.model.Book;
import com.msbook.model.User;
import com.msbook.repository.CategoryRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDtoRequest(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password, String perfilImage, @NotNull LocalDate birthDate) {

    public User bookDtoToBook() {
        return new User(username, email, password, perfilImage, birthDate);
    }
}
