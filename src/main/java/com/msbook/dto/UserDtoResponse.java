package com.msbook.dto;

import com.msbook.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record UserDtoResponse(Long id, String username, String email, String perfilImage, LocalDate birthDate) {

    public static List<UserDtoResponse> userToUserDtoList(List<User> users) {
        return users.stream().map(UserDtoResponse::userToUserDto).collect(Collectors.toList());
    }

    public static UserDtoResponse userToUserDto(User user) {
        return new UserDtoResponse(user.getId(),
                user.getName(), user.getEmail(), user.getPerfilImage(), user.getBirthDate());
    }
}