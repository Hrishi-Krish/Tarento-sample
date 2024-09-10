package com.tarento.sec.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tarento.sec.model.User;
import com.tarento.sec.model.UserPrincipal;
import com.tarento.sec.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserPrincipal(user);        
    }
}
