package com.msbook.service.serviceImpl;

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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ImageService imageService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private UserService() {
    }

    public List<UserDtoResponse> getAll() {
        List<User> users = userRepository.findAll();
        return UserDtoResponse.userToUserDtoList(users);
    }

    public UserDtoResponse getById(Long id) {
        User user = getByIdIfExists(id);
        return UserDtoResponse.userToUserDto(user);
    }

    private User getByIdIfExists(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    public UserDtoResponse create(UserDtoRequest userDto) {
        Optional<User> byEmail = userRepository.findByEmail(userDto.email());

        if (byEmail.isPresent()) {
            throw new ObjectNotFoundException("Este email já existe no sistema");
        }

        User user = userDto.bookDtoToBook();
        String passwordEncode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncode);
        User save = userRepository.save(user);

        return UserDtoResponse.userToUserDto(user);
    }

    public void update(UserDtoRequest userDtoRequest, Long id) {
        User user = getByIdIfExists(id);

        Optional<User> userByEmail = userRepository.findByEmail(userDtoRequest.email());
        if (userByEmail.isPresent() && !userByEmail.get().getId().equals(user.getId())) {
            throw new ObjectNotFoundException("Este email já existe");
        }

        user.setUsername(userDtoRequest.username());
        user.setEmail(userDtoRequest.email());
        user.setPassword(userDtoRequest.password());
        user.setPerfilImage(userDtoRequest.perfilImage());
        user.setBirthDate(userDtoRequest.birthDate());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void deleteById(Long id, AuthDtoRequest authDtoRequest) {
        User user = getByIdIfExists(id);
        if (user != null && user.getEmail().equals(authDtoRequest.email()) &&
                bCryptPasswordEncoder.matches(authDtoRequest.password(), user.getPassword())) {
            userRepository.deleteById(id);
        }
    }

    public void forgotMyPassword(ForgotPasswordRequest obj) {
        User user = userRepository.findByEmail(obj.email()).orElseThrow(() -> new ObjectNotFoundException("Email not Found"));
        if (!user.getBirthDate().equals(obj.birthDate())) {
            throw new ObjectNotFoundException("User not Found");
        }

        String token = PasswordUtils.generateRandomPassword(6);
        String message = "<span style=font-size:20px>Hello " + user.getName() + "!</span><br/><br/>"
                + "  Recebemos sua solicitação para alterar a sua senha de usuário no sistema.<br/>" +
                "    Sua nova senha é: <span style=font-weight: bold; color: #FF0000>" + token + "</span><br/>" +
                "    Para sua segurança, por favor altere sua senha na primeira vez que acessar o sistema. <br/>" +
                "    Atenciosamente,<br/>" +
                "    Equipe de Desenvolvimento.";

        findEmail(message, obj.email(), "Reset password");

        user.setPassword(bCryptPasswordEncoder.encode(token));
        userRepository.save(user);
    }

    private void findEmail(String text, String toEmail, String subject) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mail);

            message.setText(text, true);
            message.setFrom("matheuslopesnunes4@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            mailSender.send(mail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void changePassword(String password, Long id) {
        User user = getByIdIfExists(id);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }

    public void uploadImageUser(MultipartFile file, Long id) throws IOException {
//        imageService.init();
        User user = getByIdIfExists(id);

        String imageName = imageService.save(file);
        user.setPerfilImage("/user/files/" + imageName);
        userRepository.save(user);
    }
}