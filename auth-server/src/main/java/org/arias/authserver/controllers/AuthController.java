package org.arias.authserver.controllers;

import lombok.AllArgsConstructor;
import org.arias.authserver.dto.TokenDTO;
import org.arias.authserver.dto.UserDTO;
import org.arias.authserver.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.login(userDTO));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDTO> validateToken(@RequestHeader String tokenDTO) {
        return ResponseEntity.ok(authService.validateToken(tokenDTO));
    }
}
