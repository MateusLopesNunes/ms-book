package com.msbook.dto;

import com.msbook.model.Book;
import com.msbook.model.Category;
import com.msbook.repository.BookRepository;
import com.msbook.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record BookDtoRequest(String title, String synopsis, String isbn, String author, String image, Set<Long> categoriesId) {

    public Book bookDtoToBook(CategoryRepository categoryRepository) {
        Set<Category> collect = categoriesId.stream().map((x) -> {
                    return categoryRepository.findById(x).orElseThrow(() -> new RuntimeException("Categry not found"));
                }).collect(Collectors.toSet());

        return new Book(title, synopsis, isbn, author, image, collect);
    }
}
