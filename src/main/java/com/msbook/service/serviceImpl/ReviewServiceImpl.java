package com.msbook.service.serviceImpl;

import com.msbook.dto.ReviewDtoRequest;
import com.msbook.dto.ReviewDtoResponse;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Review;
import com.msbook.patternObserver.Observer;
import com.msbook.patternObserver.Subject;
import com.msbook.repository.BookRepository;
import com.msbook.repository.ReviewRepository;

import com.msbook.repository.UserRepository;
import com.msbook.service.serviceInterface.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService, Subject {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    BookServiceImpl bookService;

    private List<Observer> observers = new ArrayList<>();

    @Autowired
    public ReviewServiceImpl(BookServiceImpl bookService) {
        this.bookService = bookService;
        subscribe(bookService);
    }

    public Page<ReviewDtoResponse> getAll(Pageable page) {
        Page<Review> reviews = reviewRepository.findAll(page);
        return reviews.map(ReviewDtoResponse::reviewToDto);
    }

    public ReviewDtoResponse getById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Review not found"));
        return ReviewDtoResponse.reviewToDto(review);
    }

    public Review getByIdIfExists(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Review not found"));
    }

    public List<Review> getReviewPerBook(Long bookId) {
        return reviewRepository.findReviewPerBook(bookId);
    }

    public void create(ReviewDtoRequest reviewDto) {
        Review review = reviewRepository.save(reviewDto.reviewDtoToBook(bookRepository, userRepository));
        notifyObservers(review.getBook().getId());
    }

    public void update(ReviewDtoRequest reviewDto, Long id) {
        Review reviewModel = reviewDto.reviewDtoToBook(bookRepository, userRepository);

        Review review = getByIdIfExists(id);
        review.setRating(reviewDto.rating());
        review.setReview(reviewDto.review());
        review.setBook(reviewModel.getBook());
        review.setUser(reviewModel.getUser());
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        getByIdIfExists(id);
        reviewRepository.deleteById(id);
    }

    // Observer pattern

    @Override
    public void subscribe(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Long id) {
        System.out.println(observers.get(0));
        observers.forEach(observer -> observer.update(id));
    }
}
