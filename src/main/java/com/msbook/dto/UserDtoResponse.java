package com.msbook.dto;

import com.msbook.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record UserDtoResponse(String username, String email, String perfilImage, LocalDate birthDate) {

    public static List<UserDtoResponse> userToUserDtoList(List<User> users) {
        return users.stream().map(UserDtoResponse::userToUserDto).collect(Collectors.toList());
    }

    public static UserDtoResponse userToUserDto(User user) {
        return new UserDtoResponse(
                user.getUsername(), user.getEmail(), user.getPerfilImage(), user.getBirthDate());
    }
}