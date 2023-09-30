package com.msbook.controller;

import com.msbook.dto.*;
import com.msbook.model.User;
import com.msbook.service.serviceImpl.AuthService;
import com.msbook.service.serviceImpl.ImageService;
import com.msbook.service.serviceImpl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public Iterable<UserDtoResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDtoResponse getById(@PathVariable Long id) {
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
    public ResponseEntity deleteById(@RequestParam String token, @PathVariable Long id, @RequestBody @Valid AuthDtoRequest authDtoRequest) {
        if (authService.autheticated(token, id)) {
            userService.deleteById(id, authDtoRequest);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/resetPassword")
    @Transactional
    public void forgotMyPassword(@RequestBody @Valid ForgotPasswordRequest obj) {
        userService.forgotMyPassword(obj);
    }

    @PostMapping("/perfil/{id}")
    public void uploadImageUser(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        userService.uploadImageUser(file, id);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@RequestParam String token, @RequestParam Long id, @PathVariable String filename) {
        if (authService.autheticated(token, id)) {
            Resource file = imageService.load(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid AuthDtoRequest authDtoRequest) {
        return authService.login(authDtoRequest);
    }
}

