package com.msbook.dto;

import com.msbook.model.Book;
import com.msbook.model.User;
import com.msbook.repository.CategoryRepository;

import java.time.LocalDate;

public record UserDtoRequest(String name, String email, String password, String perfilImage, LocalDate birthDate) {

    public User bookDtoToBook() {
        return new User(name, email, password, perfilImage, birthDate);
    }
}
