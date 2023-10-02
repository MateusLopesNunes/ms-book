package com.msbook.controller;

import com.msbook.dto.ReviewDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Review;
import com.msbook.repository.UserRepository;
import com.msbook.service.serviceImpl.AuthService;
import com.msbook.service.serviceImpl.ReviewService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    AuthService authService;

    @GetMapping("")
    public ResponseEntity<Page<Review>> getAll(@RequestParam String token, @RequestParam Long id, Pageable page) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(reviewService.getAll(page));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getById(@RequestParam String token, @PathVariable Long id) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(reviewService.getById(id));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ReviewDtoRequest reviewDto, @RequestParam String token, @RequestParam Long id) {
        if (authService.autheticated(token, id)) {
            reviewService.create(reviewDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@RequestBody @Valid ReviewDtoRequest reviewDto, @PathVariable Long id, @RequestParam String token) {
        if (authService.autheticated(token, id)) {
            reviewService.update(reviewDto, id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id, @RequestParam String token) {
        if (authService.autheticated(token, id)) {
            reviewService.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
