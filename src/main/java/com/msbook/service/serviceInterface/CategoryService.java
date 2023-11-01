package com.msbook.service.serviceInterface;

import com.msbook.dto.CategoryDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Category;
import com.msbook.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public List<Category> getAll();

    public Category getById(Long id);

    public void create(CategoryDtoRequest categoryDto);

    public void update(CategoryDtoRequest categoryDto, Long id);

    public void deleteById(Long id);
}
