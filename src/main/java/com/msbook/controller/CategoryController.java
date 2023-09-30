package com.msbook.controller;

import com.msbook.dto.CategoryDtoRequest;
import com.msbook.model.Category;
import com.msbook.service.serviceImpl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Iterable<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryDtoRequest categoryDto) {
        categoryService.create(categoryDto);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody CategoryDtoRequest categoryDto, @PathVariable Long id) {
        categoryService.update(categoryDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
