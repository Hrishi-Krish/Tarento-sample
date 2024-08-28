package dev.hrishi.app.service;

import dev.hrishi.app.model.Users;
import dev.hrishi.app.model.UsersPrincipal;
import dev.hrishi.app.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepo.findByEmail(username);
        if (users == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User with email " + username + " not found");
        }

        return new UsersPrincipal(users);
    }
}
