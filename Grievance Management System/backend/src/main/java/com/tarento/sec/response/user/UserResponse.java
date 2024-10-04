package com.tarento.sec.response.user;

import lombok.Data;

@Data
public class UserResponse {

    private long id;
    private String username;
    private String email;
    private String roleName;
    
}
