package com.msbook.service.serviceImpl;

import com.msbook.dto.AuthorDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Author;
import com.msbook.repository.AuthorRepository;
import com.msbook.service.serviceInterface.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    public void create(AuthorDtoRequest authorDtoRequest) {
        Author author = new Author(authorDtoRequest.name());
        authorRepository.save(author);
    }

    public void update(AuthorDtoRequest authorDtoRequest, Long id) {
        Author author = getById(id);
        author.setName(authorDtoRequest.name());
        authorRepository.save(author);
    }

    public void deleteById(Long id) {
        getById(id);
        authorRepository.deleteById(id);
    }

}
