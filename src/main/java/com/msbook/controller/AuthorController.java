package com.msbook.controller;

import com.msbook.dto.AuthorDtoRequest;
import com.msbook.model.Author;
import com.msbook.service.serviceImpl.AuthServiceImpl;
import com.msbook.service.serviceImpl.AuthorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorServiceImpl authorService;

    @Autowired
    AuthServiceImpl authService;

    @GetMapping
    public ResponseEntity<Iterable<Author>> getAll(@RequestParam String token, @RequestParam Long id) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(authorService.getAll());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        return authorService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid AuthorDtoRequest authorDtoRequest) {
        authorService.create(authorDtoRequest);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid AuthorDtoRequest authorDtoRequest, @PathVariable Long id) {
        authorService.update(authorDtoRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }

}
