package com.msbook.repository;

import com.msbook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM Book WHERE UPPER(title) LIKE UPPER(CONCAT('%', :name, '%'))", nativeQuery = true)
    public List<Book> findByTitle(@Param("name") String name);

//    Book findByCategoryName(String name);
}
