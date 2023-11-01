package com.msbook.dto;

import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Book;
import com.msbook.model.Review;
import com.msbook.model.User;
import com.msbook.repository.BookRepository;
import com.msbook.repository.UserRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReviewDtoRequest(@NotNull Float rating, @NotBlank @Max(2000) String review, @NotNull Long bookId, @NotNull Long userId) {

    public Review reviewDtoToBook(BookRepository bookRepository, UserRepository userRepository) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ObjectNotFoundException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        return new Review(rating, review, book, user);
    }
}
