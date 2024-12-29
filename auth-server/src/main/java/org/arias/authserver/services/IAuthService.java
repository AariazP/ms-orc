package org.arias.authserver.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.arias.authserver.dto.TokenDTO;
import org.arias.authserver.dto.UserDTO;
import org.arias.authserver.entities.User;
import org.arias.authserver.helpers.JWTHelper;
import org.arias.authserver.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class IAuthService implements AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTHelper jwtHelper;
    private static final String MESSAGE = "Invalid password";
    private static final String USER_NOT_FOUND = "User not found";

    @Override
    public TokenDTO login(UserDTO userDTO) {
        final var user = usersRepository.findByUsername(userDTO.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_NOT_FOUND));
        this.validateUser(userDTO, user);
        return new TokenDTO(jwtHelper.createToken(user.getUsername()));
    }

    @Override
    public TokenDTO validateToken(String tokenDTO) {
       if(jwtHelper.validateToken(tokenDTO)) {
           return new TokenDTO(tokenDTO);
       }
         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, MESSAGE);
    }

    private boolean validateUser(UserDTO userDTO, User user) {

        if(!passwordEncoder.matches(userDTO.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, MESSAGE);
        }

        return true;
    }
}
