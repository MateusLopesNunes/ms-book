package com.msbook.dto;

import com.msbook.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public record BookAuthorsDtoResponse(Long id, String author) {
    public static List<BookAuthorsDtoResponse> bookToBookAuthorsDtoResponse(List<Book> allAuthors) {
        return allAuthors.stream().map(book -> new BookAuthorsDtoResponse(book.getId(), book.getAuthor())).collect(Collectors.toList());
    }
}
