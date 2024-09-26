package com.tarento.sec.response;

import lombok.Data;

@Data
public class UserLoginResponse {

    private long id;
    private String username;
    private String role;

    public UserLoginResponse(long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    
}
