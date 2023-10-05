package com.msbook.service.serviceImpl;

import com.msbook.dto.AuthDtoRequest;
import com.msbook.dto.TokenResponse;
import com.msbook.dto.exception.ObjectNotFoundException;
import com.msbook.model.User;
import com.msbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public boolean autheticated(String token, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não existe"));
        if (user.getToken().equals(token)) {
            return true;
        }
        return false;
    }

    public TokenResponse login(AuthDtoRequest authDtoRequest) {
        Optional<User> user = userRepository.findByEmail(authDtoRequest.email());
        if (user.isPresent() && user.get().getEmail().equals(authDtoRequest.email()) &&
                bCryptPasswordEncoder.matches(authDtoRequest.password(), user.get().getPassword())) {
            return new TokenResponse(updateToken(user.get().getId()), user.get().getId());
        }
        else {
            throw new ObjectNotFoundException("Credenciais inválidas");
        }
    }

    public void resetToken(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não existe"));
        user.setToken(null);
        userRepository.save(user);
    }

    private String updateToken(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não existe"));
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userRepository.save(user);
        return token;
    }
}
