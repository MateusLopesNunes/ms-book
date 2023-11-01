package com.msbook.service.serviceInterface;

import com.msbook.dto.*;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.User;
import com.msbook.repository.UserRepository;
import com.msbook.util.PasswordUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    public List<UserDtoResponse> getAll();

    public UserDtoResponse getById(Long id);

    public UserDtoResponse create(UserDtoRequest userDto);

    public void update(UserDtoUpdateRequest userDtoRequest, Long id);

    public void deleteById(Long id, AuthDtoRequest authDtoRequest);

    public void forgotMyPassword(ForgotPasswordRequest obj);

    public void changePassword(String password, Long id);

    public void uploadImageUser(MultipartFile file, Long id) throws IOException;
}