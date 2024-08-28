package dev.hrishi.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsersDto {

    private String username;
    private String password;
    private String email;
    private String role;
}
