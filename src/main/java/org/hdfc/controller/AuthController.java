package org.hdfc.controller;


import org.hdfc.dto.LoginResponse;
import org.hdfc.jwt.JwtService;
import org.hdfc.model.User;
import org.hdfc.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user){
        String token;
        try{
            token=authService.login(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new LoginResponse(token));
    }


}
