package com.msbook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.Token;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    private String email;
    private String password;
    private String perfilImage;
    private LocalDate birthDate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private String token;

    public User() {
    }

    public User(String username, String email, String password, String perfilImage, LocalDate birthDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.perfilImage = perfilImage;
        this.birthDate = birthDate;
    }

    public User(String username, String email, String perfilImage, LocalDate birthDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.perfilImage = perfilImage;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerfilImage() {
        return perfilImage;
    }

    public void setPerfilImage(String perfilImage) {
        this.perfilImage = perfilImage;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
