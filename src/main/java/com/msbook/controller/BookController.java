package com.msbook.controller;

import com.msbook.dto.BookDtoRequest;
import com.msbook.model.Book;
import com.msbook.service.serviceImpl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping
    Iterable<Book> getAll() {
        return bookService.getAll();
    }

//    @GetMapping("/{id}")
//    Iterable<Book> getById(@PathVariable Long id) {
//        return bookService.get();
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody BookDtoRequest bookRequest) {
        bookService.create(bookRequest);
    }

}
