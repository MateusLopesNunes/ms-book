package com.msbook.service.serviceImpl;

import com.msbook.dto.CategoryDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Category;
import com.msbook.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
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
    }
}
