package com.msbook.service.serviceInterface;

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
public interface AuthService {

    public boolean autheticated(String token, Long id);

    public TokenResponse login(AuthDtoRequest authDtoRequest);

    public void resetToken(Long id);
}
