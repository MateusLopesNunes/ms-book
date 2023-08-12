package com.msbook.controller;

import com.msbook.dto.BookDtoRequest;
import com.msbook.model.Book;
import com.msbook.service.serviceImpl.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping
    Page<Book> getAll(Pageable page) {
        return bookService.getAll(page);
    }

    @GetMapping("/filter/name/{title}")
    Iterable<Book> getByTitle(@PathVariable String title) {
        return bookService.getByTitle(title);
    }

    @GetMapping("/{id}")
    Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/filter/category")
    Iterable<Book> getPerCategoryName() {
        return bookService.getPerCategoryName();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody @Valid BookDtoRequest bookRequest) {
        bookService.create(bookRequest);
    }

    @PutMapping("/{id}")
    void update(@RequestBody @Valid BookDtoRequest bookRequest, @PathVariable Long id) {
        bookService.update(bookRequest, id);
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id) {
        bookService.getById(id);
    }
}
