package org.hdfc.controller;


import org.hdfc.dto.LoginResponse;
import org.hdfc.exception.InvalidCredentialsException;
import org.hdfc.exception.InvalidTokenException;
import org.hdfc.jwt.JwtService;
import org.hdfc.model.User;
import org.hdfc.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user){
        logger.info(
                "POST /login request received for username: {}",
                user.getUsername()
        );
        String token;
        try{
            token=authService.login(user.getUsername(), user.getPassword());
            logger.info(
                    "POST /login completed successfully for username: {}",
                    user.getUsername()
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        return ResponseEntity.ok(new LoginResponse(token));
    }


}
