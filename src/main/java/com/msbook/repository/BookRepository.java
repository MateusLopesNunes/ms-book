package com.msbook.repository;

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

    @Query(value = "SELECT rating FROM Review WHERE book.id = :id")
    List<Float> findByRatingPerBook(@Param("id") Long id);

    List<Book> findByTitleContainingOrAuthorIdOrCategoriesIdInAndTotalBookRatingGreaterThanEqual(
            String title, Long authorId, List<Long> categoriesId, Float totalBookRating);

//    @Query("SELECT DISTINCT b FROM Book b \n" +
//            "JOIN b.categories c JOIN b.author a \n" +
//            "WHERE\n" +
//            "    (b.title LIKE '%:title%' OR\n" +
//            "    a.id = :authorId)\n" +
//            "    AND\n" +
//            "    c.id IN (:categoriesId)\n" +
//            "    AND\n" +
//            "    b.totalBookRating >= :rating")
//    public List<Book> findByFilters(@Param("title") String title, @Param("authorId") Long authorId,
//                                    @Param("categoriesId") List<Long> categoriesId, @Param("rating") Float rating);
//
//    List<Book> findByTitleContainingAndAuthorIdOrCategoriesIdInAndTotalBookRatingGreaterThanEqual(
//            String title, Long authorId, List<Long> categoriesId, Float totalBookRating);

}

