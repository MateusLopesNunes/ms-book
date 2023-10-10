package com.msbook.service.serviceImpl;

import com.msbook.dto.BookDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Book;
import com.msbook.repository.BookRepository;
import com.msbook.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ImageService imageService;


    public Page<Book> getAll(Pageable page) {
        return bookRepository.findAll(page);
    }

    public List<String> getAllAuthors() {
        return bookRepository.findAllAuthors();
    }

    public Iterable<Book> getByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Iterable<Book> getPerCategoryName(Long categoryId) {
        return bookRepository.findBookPerCategory(categoryId);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book not Found"));
    }

    public void create(BookDtoRequest bookRequest) {
        Book book = bookRequest.bookDtoToBook(categoryRepository);
        bookRepository.save(book);
    }

    public void update(BookDtoRequest bookRequest, Long id) {
        Book bookModel = bookRequest.bookDtoToBook(categoryRepository);

        Book book = getById(id);
        book.setTitle(bookModel.getTitle());
        book.setAuthor(bookModel.getAuthor());
        book.setSynopsis(bookModel.getSynopsis());
        book.setUpdated_at(LocalDateTime.now());
        book.setCategories(bookModel.getCategories());
        bookRepository.save(book);
    }

    public void deleteById(Long id) {
        getById(id);
        bookRepository.deleteById(id);
    }

    public void uploadImageBook(MultipartFile file, Long id) {
        Book book = getById(id);

        String imageName = imageService.save(file);
        book.setImage("/book/files/" + imageName);
        bookRepository.save(book);
    }

    public List<Book> getFilter() {
        return bookRepository.findByFilters();
    }
}
