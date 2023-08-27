package com.msbook.controller;

import com.msbook.dto.UserDtoRequest;
import com.msbook.model.User;
import com.msbook.service.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Iterable<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void create(@RequestBody UserDtoRequest userRequest) {
        userService.create(userRequest);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody UserDtoRequest userRequest, @PathVariable Long id) {
        userService.update(userRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
