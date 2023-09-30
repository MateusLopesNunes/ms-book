package com.msbook.service.serviceImpl;

import com.msbook.dto.CategoryDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Category;
import com.msbook.model.Review;
import com.msbook.repository.CategoryRepository;
import com.msbook.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    /* @Autowired
    ReviewRepository reviewRepository;

    public List<Review> getAll() {
        return categoryRepository.findAll();
    }

    public Review getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    public void create(CategoryDtoRequest categoryDto) {
        Category category = new Category(categoryDto.name());
        categoryRepository.save(category);
    }

    public void update(CategoryDtoRequest categoryDto, Long id) {
        Category category = getById(id);
        category.setName(categoryDto.name());
        categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        getById(id);
        categoryRepository.deleteById(id);
    } */
}
