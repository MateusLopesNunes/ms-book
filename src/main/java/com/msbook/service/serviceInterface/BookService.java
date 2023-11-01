package com.msbook.service.serviceInterface;

import com.msbook.dto.BookDtoRequest;
import com.msbook.dto.FiltersBookDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Book;
import com.msbook.patternObserver.Observer;
import com.msbook.repository.AuthorRepository;
import com.msbook.repository.BookRepository;
import com.msbook.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public interface BookService {

    public Page<Book> getAll(Pageable page);

    public Iterable<Book> getByTitle(String title);

    public Iterable<Book> getPerCategoryName(Long categoryId);

    public Book getById(Long id);

    public void create(BookDtoRequest bookRequest);

    public void update(BookDtoRequest bookRequest, Long id);

    public void deleteById(Long id);

    public void uploadImageBook(MultipartFile file, Long id);

    public List<Book> getFilter(FiltersBookDtoRequest filters);

    public void updateRating(Long id);
}
