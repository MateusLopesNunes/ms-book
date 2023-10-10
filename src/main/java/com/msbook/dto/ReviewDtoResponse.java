package com.msbook.dto;

import com.msbook.model.Book;
import com.msbook.model.Review;
import com.msbook.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record ReviewDtoResponse(Long id, Float rating, String createdAt, String updatedAt, String review, Book bookId, User userId) {

    public static ReviewDtoResponse reviewToDto(Review review) {
        String createdAtFormat = review.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        if (review.getUpdatedAt() == null) {
            return new ReviewDtoResponse(review.getId(), review.getRating(), createdAtFormat, null, review.getReview(), review.getBook(), review.getUser());
        }

        String updateAtFormat = review.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return new ReviewDtoResponse(review.getId(), review.getRating(), createdAtFormat, updateAtFormat, review.getReview(), review.getBook(), review.getUser());
    }
}
