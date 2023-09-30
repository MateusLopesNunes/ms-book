package com.msbook.repository;

import com.msbook.dto.BookAuthorsDtoResponse;
import com.msbook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM Book WHERE UPPER(title) LIKE UPPER(CONCAT('%', :name, '%'))", nativeQuery = true)
    public List<Book> findByTitle(@Param("name") String name);

    @Query(value = "select * from book join book_categories on book.id = book_categories.book_id where book_categories.categories_id = :categoryId", nativeQuery = true)
    Iterable<Book> findBookPerCategory(@Param("categoryId") Long categoryId);

    @Query(value = "select distinct author from book", nativeQuery = true)
    public List<String> findAllAuthors();

}

