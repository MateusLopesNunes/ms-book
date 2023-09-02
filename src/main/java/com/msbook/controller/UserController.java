package com.msbook.controller;

import com.msbook.dto.ForgotPasswordRequest;
import com.msbook.dto.UserDtoRequest;
import com.msbook.dto.UserDtoResponse;
import com.msbook.model.User;
import com.msbook.service.serviceImpl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Iterable<UserDtoResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void create(@RequestBody @Valid UserDtoRequest userRequest) {
        userService.create(userRequest);
    }

    @PutMapping("/{id}")
    @Transactional
    public void update(@RequestBody @Valid UserDtoRequest userRequest, @PathVariable Long id) {
        userService.update(userRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping("/resetPassword")
    @Transactional
    public void forgotMyPassword(@RequestBody @Valid ForgotPasswordRequest obj) {
        userService.forgotMyPassword(obj);
    }

}
