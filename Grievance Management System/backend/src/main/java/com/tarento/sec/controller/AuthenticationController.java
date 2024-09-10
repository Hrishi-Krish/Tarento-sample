package com.tarento.sec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarento.sec.component.JwtUtils;
import com.tarento.sec.dto.AuthenticationRequest;
import com.tarento.sec.dto.AuthenticationResponse;
import com.tarento.sec.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
