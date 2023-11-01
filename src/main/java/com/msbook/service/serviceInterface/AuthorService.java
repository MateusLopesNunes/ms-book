package com.msbook.service.serviceInterface;

import com.msbook.dto.AuthorDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Author;
import com.msbook.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    public Author getById(Long id);

    public void create(AuthorDtoRequest authorDtoRequest);

    public void update(AuthorDtoRequest authorDtoRequest, Long id);

    public void deleteById(Long id);

}
