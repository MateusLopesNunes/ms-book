package com.msbook.model;

import com.msbook.model.enumeration.StatusBook;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String synopsis;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime updated_at;
    private String isbn;
    private Long total_book_rating = 0L;
    private String author;
    private String image;
    private StatusBook status = StatusBook.PENDING;

    @ManyToMany
    private Set<Category> categories = new HashSet<>();

    public Book() {
    }

    public Book(String title, String synopsis, String isbn, String author, String image, Set<Category> categories) {
        this.title = title;
        this.synopsis = synopsis;
        this.isbn = isbn;
        this.author = author;
        this.image = image;
        categories.forEach(x -> {
            this.categories.add(x);
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getTotal_book_rating() {
        return total_book_rating;
    }

    public void setTotal_book_rating(Long total_book_rating) {
        this.total_book_rating = total_book_rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public StatusBook getStatus() {
        return status;
    }

    public void setStatus(StatusBook status) {
        this.status = status;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
