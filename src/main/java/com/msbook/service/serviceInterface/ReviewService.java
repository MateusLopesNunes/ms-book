package com.msbook.service.serviceInterface;

import com.msbook.dto.ReviewDtoRequest;
import com.msbook.dto.ReviewDtoResponse;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Review;
import com.msbook.patternObserver.Observer;
import com.msbook.patternObserver.Subject;
import com.msbook.repository.BookRepository;
import com.msbook.repository.ReviewRepository;
import com.msbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public interface ReviewService {

    public Page<ReviewDtoResponse> getAll(Pageable page);

    public ReviewDtoResponse getById(Long id);

    public Review getByIdIfExists(Long id);

    public List<Review> getReviewPerBook(Long bookId);

    public void create(ReviewDtoRequest reviewDto);

    public void update(ReviewDtoRequest reviewDto, Long id);

    public void deleteById(Long id);
}
