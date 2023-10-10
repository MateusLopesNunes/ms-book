package com.msbook.repository;

import com.msbook.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select * from review where book_id = :bookId", nativeQuery = true)
    List<Review> findReviewPerBook(@Param("bookId") Long bookId);
}