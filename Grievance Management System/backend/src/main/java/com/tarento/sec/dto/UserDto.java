package com.tarento.sec.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String password;
    private String role;

    public UserDto(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
