package org.hdfc.store;

import org.hdfc.model.User;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {
    private final Map<String,String> tokens = new ConcurrentHashMap<>();

    public boolean addToken(String token, String username){
        if(tokens.containsKey(token)){
            return false;
        }
        tokens.put(token, username);
        return true;
    }

    public boolean tokenExists(String token){
        return tokens.containsKey(token);
    }

    public void deleteToken(String token){
         tokens.remove(token);
    }
}
