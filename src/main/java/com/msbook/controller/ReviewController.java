package com.msbook.controller;

import com.msbook.dto.ReviewDtoRequest;
import com.msbook.dto.ReviewDtoResponse;
import com.msbook.model.Review;
import com.msbook.service.serviceImpl.AuthServiceImpl;
import com.msbook.service.serviceImpl.ReviewServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    ReviewServiceImpl reviewService;

    @Autowired
    AuthServiceImpl authService;

    @GetMapping("")
    public ResponseEntity<Page<ReviewDtoResponse>> getAll(@RequestParam String token, @RequestParam Long id, Pageable page) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(reviewService.getAll(page));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDtoResponse> getById(@RequestParam String token, @RequestParam Long id, @PathVariable Long reviewId) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(reviewService.getById(id));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getReviewPerBook(@RequestParam String token, @RequestParam Long id, @PathVariable Long bookId) {
        if (authService.autheticated(token, id)) {
            return ResponseEntity.ok(reviewService.getReviewPerBook(bookId));
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

    @PutMapping("/{reviewId}")
    @Transactional
    public ResponseEntity update(@PathVariable Long reviewId, @RequestBody @Valid ReviewDtoRequest reviewDto, @RequestParam String token, @RequestParam Long id) {
        if (authService.autheticated(token, id)) {
            reviewService.update(reviewDto, reviewId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity deleteById(@RequestParam String token, @RequestParam Long id, @PathVariable Long reviewId) {
        if (authService.autheticated(token, id)) {
            reviewService.deleteById(reviewId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
