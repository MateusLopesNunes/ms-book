package com.msbook.dto;

import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Author;
import com.msbook.model.Book;
import com.msbook.model.Category;
import com.msbook.repository.AuthorRepository;
import com.msbook.repository.BookRepository;
import com.msbook.repository.CategoryRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record BookDtoRequest(@NotBlank String title, @NotBlank String synopsis, @NotNull Long authorId, @NotNull Set<Long> categoriesId) {

    public Book bookDtoToBook(CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));

        Set<Category> categories = categoriesId.stream().map((x) -> {
                    return categoryRepository.findById(x).orElseThrow(() -> new RuntimeException("Categry not found"));
                }).collect(Collectors.toSet());

        if (synopsis.length() >= 1000) {
            throw new ObjectNotFoundException("Sinopse deve ser menor que ou igual à 1000");
        }

        return new Book(title, synopsis, author, categories);
    }
}
