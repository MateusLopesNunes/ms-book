package com.msbook.service.serviceImpl;

import com.msbook.dto.UserDtoRequest;
import com.msbook.dto.UserDtoResponse;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.User;
import com.msbook.repository.UserRepository;
import com.msbook.util.PasswordUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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

    public List<UserDtoResponse> getAll() {
        List<User> users = userRepository.findAll();
        return UserDtoResponse.userToUserDtoList(users);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    public void create(UserDtoRequest userDto) {
        Optional<User> byEmail = userRepository.findByEmail(userDto.email());

        if (byEmail.isPresent()) {
            throw new ObjectNotFoundException("Este email já existe no sistema");
        }

        User user = userDto.bookDtoToBook();
        userRepository.save(user);
    }

    public void update(UserDtoRequest userDtoRequest, Long id) {
        User user = getById(id);

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

    public void deleteById(Long id) {
        getById(id);
        userRepository.deleteById(id);
    }

    public void forgotMyPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Email not Found"));
        String token = PasswordUtils.generateRandomPassword(6);

        String message = "<span style=\"font-size:20px\">Hello " + user.getUsername() + "!</span><br/><br/>"
                + "  \"Recebemos sua solicitação para alterar a senha do Diretório de Contatos do sistema.<br/>\"\n" +
                "    \"Sua nova senha é: <span style=\"font-weight: bold; color: #FF0000\">" + token + "</span><br/>\"\n" +
                "    \"Para sua segurança, por favor altere sua senha na primeira vez que acessar o sistema. <br/>\"\n" +
                "    \"Atenciosamente,<br/>\"\n" +
                "    \"Equipe de Desenvolvimento.\".";

        findEmail(message, email, "Reset password");

        user.setPassword(token);
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
}
