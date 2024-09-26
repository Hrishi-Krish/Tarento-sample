package com.tarento.sec.dto;

import com.tarento.sec.response.UserLoginResponse;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private final String jwt;
    private final UserLoginResponse response;

    public AuthenticationResponse(String jwt, UserLoginResponse response) {
        this.jwt = jwt;
        this.response = response;
    }

}
