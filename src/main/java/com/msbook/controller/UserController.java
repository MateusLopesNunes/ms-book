package com.msbook.controller;

import com.msbook.dto.*;
import com.msbook.service.serviceImpl.AuthServiceImpl;
import com.msbook.service.serviceImpl.ImageServiceImpl;
import com.msbook.service.serviceImpl.UserServiceImpl;
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
    UserServiceImpl userService;

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    private ImageServiceImpl imageService;

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
    public UserDtoResponse create(@RequestBody @Valid UserDtoRequest userRequest) {
        return userService.create(userRequest);
    }

    @PutMapping("/{id}")
    @Transactional
    public void update(@RequestBody @Valid UserDtoUpdateRequest userRequest, @PathVariable Long id) {
        userService.update(userRequest, id);
    }

    @PostMapping("/delete/{id}")
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
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = imageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid AuthDtoRequest authDtoRequest) {
        return authService.login(authDtoRequest);
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable Long id) {
        authService.resetToken(id);
        return ResponseEntity.ok().build();
    }
}

