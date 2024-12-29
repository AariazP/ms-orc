package org.arias.authserver.services;

import org.arias.authserver.dto.TokenDTO;
import org.arias.authserver.dto.UserDTO;

public interface AuthService {
    TokenDTO login(UserDTO userDTO);
    TokenDTO validateToken(TokenDTO tokenDTO);
}
