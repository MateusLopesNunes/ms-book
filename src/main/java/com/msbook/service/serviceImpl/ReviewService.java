package com.msbook.service.serviceImpl;

import com.msbook.dto.CategoryDtoRequest;
import com.msbook.dto.ReviewDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Book;
import com.msbook.model.Category;
import com.msbook.model.Review;
import com.msbook.repository.BookRepository;
import com.msbook.repository.CategoryRepository;
import com.msbook.repository.ReviewRepository;

import com.msbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    public Page<Review> getAll(Pageable page) {
        return reviewRepository.findAll(page);
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    public void create(ReviewDtoRequest reviewDto) {
        reviewRepository.save(reviewDto.reviewDtoToBook(bookRepository, userRepository));
    }

    public void update(ReviewDtoRequest reviewDto, Long id) {
        Review reviewModel = reviewDto.reviewDtoToBook(bookRepository, userRepository);

        Review review = getById(id);
        review.setRating(reviewDto.rating());
        review.setReview(reviewDto.review());
        review.setBook(reviewModel.getBook());
        review.setUser(reviewModel.getUser());
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        getById(id);
        reviewRepository.deleteById(id);
    }
}
