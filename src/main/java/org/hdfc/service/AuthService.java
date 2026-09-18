package org.hdfc.service;

import io.micrometer.observation.Observation;
import org.hdfc.dto.LoginResponse;
import org.hdfc.exception.InvalidCredentialsException;
import org.hdfc.exception.SessionInvalidException;
import org.hdfc.jwt.JwtService;
import org.hdfc.store.TokenStore;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final TokenStore tokenStore;

    public AuthService(JwtService jwtService, TokenStore tokenStore) {
        this.jwtService = jwtService;
        this.tokenStore = tokenStore;
    }

    String user="Mitesh";
    String pass="12345";
    public String login(String username, String password){

        if(!username.equals(user) || !password.equals(pass)){
            throw new InvalidCredentialsException("Invalid credentials");
        }
            String token=jwtService.generateToken(username);
            boolean flag=tokenStore.addToken(token,username);
            return token;
    }

    public String authenticate(String token) {
        // Step 1: Cryptographically validate JWT
        String username = String.valueOf(jwtService
                .validateToken(token));
        return username;
    }

    public void logout(String token){
        tokenStore.deleteToken(token);
    }
}
