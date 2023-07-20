package com.msbook.dto;

import com.msbook.model.Book;
import com.msbook.model.Category;
import com.msbook.repository.BookRepository;
import com.msbook.repository.CategoryRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record BookDtoRequest(@NotBlank String title, @NotBlank String synopsis, @NotBlank String isbn, @NotBlank String author, @NotBlank String image, @NotNull Set<Long> categoriesId) {

    public Book bookDtoToBook(CategoryRepository categoryRepository) {
        Set<Category> collect = categoriesId.stream().map((x) -> {
                    return categoryRepository.findById(x).orElseThrow(() -> new RuntimeException("Categry not found"));
                }).collect(Collectors.toSet());

        return new Book(title, synopsis, isbn, author, image, collect);
    }
}
