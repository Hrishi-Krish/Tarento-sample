package com.tarento.sec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarento.sec.component.JwtUtils;
import com.tarento.sec.dto.AuthenticationRequest;
import com.tarento.sec.dto.AuthenticationResponse;
import com.tarento.sec.service.CustomUserDetailsService;
import com.tarento.sec.service.TokenBlacklistService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils, TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.tokenBlacklistService = tokenBlacklistService;
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            tokenBlacklistService.blacklistToken(jwt);
            return ResponseEntity.ok().body("Logged out successfully");
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }
}
