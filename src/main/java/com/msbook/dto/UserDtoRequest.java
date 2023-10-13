package com.msbook.dto;

import com.msbook.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDtoRequest(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password, @NotNull LocalDate birthDate) {

    public User bookDtoToBook() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(birthDate);
        return user;
    }
}
