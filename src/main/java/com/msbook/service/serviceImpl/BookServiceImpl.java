package com.msbook.service.serviceImpl;

import com.msbook.dto.BookDtoRequest;
import com.msbook.dto.FiltersBookDtoRequest;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.Book;
import com.msbook.patternObserver.Observer;
import com.msbook.repository.AuthorRepository;
import com.msbook.repository.BookRepository;
import com.msbook.repository.CategoryRepository;
import com.msbook.service.serviceInterface.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class BookServiceImpl implements BookService, Observer {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private ImageServiceImpl imageService;


    public Page<Book> getAll(Pageable page) {
        return bookRepository.findAll(page);
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
        Book book = bookRequest.bookDtoToBook(categoryRepository, authorRepository);
        bookRepository.save(book);
    }

    public void update(BookDtoRequest bookRequest, Long id) {
        Book bookModel = bookRequest.bookDtoToBook(categoryRepository, authorRepository);

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

    public List<Book> getFilter(FiltersBookDtoRequest filters) {
        List<Long> categoriesId = filters.categoriesId();
        if (categoriesId == null) {
            categoriesId = Arrays.asList();
        }
        return bookRepository.findByTitleContainingOrAuthorIdOrCategoriesIdInAndTotalBookRatingGreaterThanEqual(filters.title(), filters.authorId(), categoriesId, filters.rating());
    }

    public void updateRating(Long id) {
        System.out.println("Updating rating... " + id);
        List<Float> ratings = bookRepository.findByRatingPerBook(id);
        Float average = ratings.stream().reduce((a, b) -> a + b).map(sum -> sum / ratings.size()).orElseThrow(() -> new ObjectNotFoundException("Erro inesperado na média"));
        System.out.println("Average rating: " + average);

        Book book = getById(id);
        book.setTotalBookRating(average);
        bookRepository.save(book);
    }

    // Observer pattern

    @Override
    public void update(Long id) {
        updateRating(id);
    }
}
