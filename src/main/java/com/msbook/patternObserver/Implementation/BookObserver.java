package com.msbook.patternObserver.Implementation;

import com.msbook.model.Book;
import com.msbook.model.Review;
import com.msbook.patternObserver.interfaces.Observer;
import com.msbook.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookObserver implements Observer {

    private BookRepository bookRepository;

    public BookObserver(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void update(Review review) {
        List<Float> ratings = bookRepository.findByRatingPerBook(review.getBook().getId());
        Optional<Float> average = ratings.stream().reduce((a, b) -> a + b).map(sum -> sum / ratings.size());
        System.out.println("Average rating: " + average.get());

        Book book = review.getBook();
        book.setTotalBookRating(average.get());
        bookRepository.save(book);
    }
}
