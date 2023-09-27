package com.msbook.controller;

import com.msbook.dto.BookDtoRequest;
import com.msbook.model.Book;
import com.msbook.service.serviceImpl.AuthService;
import com.msbook.service.serviceImpl.BookService;
import com.msbook.service.serviceImpl.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    AuthService authService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/")
    ResponseEntity<Page<Book>> getAll(@RequestParam String token, @RequestParam Long id, Pageable page) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(bookService.getAll(page));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/filter/name/{title}")
    Iterable<Book> getByTitle(@PathVariable String title) {
        return bookService.getByTitle(title);
    }

    @GetMapping("/{bookId}")
    ResponseEntity<Book> getById(@RequestParam String token, @RequestParam Long id, @PathVariable Long bookId) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(bookService.getById(bookId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/category")
    ResponseEntity<Iterable<Book>> getPerCategoryName(@RequestParam Long bookId, @RequestParam Long categoryId, @RequestParam String token, @RequestParam Long id) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(bookService.getPerCategoryName(bookId, categoryId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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

    @PostMapping("/bookCover/{bookId}")
    public ResponseEntity uploadImageBook(@RequestParam String token, @RequestParam Long id, @PathVariable Long bookId, @RequestPart("file") MultipartFile file) throws IOException {
        if (authService.autheticated(token, id)) {
            bookService.uploadImageBook(file, bookId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@RequestParam String token, @RequestParam Long id, @PathVariable String filename) {
        if (authService.autheticated(token, id)) {
            Resource file = imageService.load(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
