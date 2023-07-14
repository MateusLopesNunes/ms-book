package com.msbook.service.serviceImpl;

import com.msbook.dto.BookDtoRequest;
import com.msbook.model.Book;
import com.msbook.repository.BookRepository;
import com.msbook.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    public void create(BookDtoRequest bookRequest) {
        Book book = bookRequest.bookDtoToBook(categoryRepository);
        bookRepository.save(book);
    }

}
