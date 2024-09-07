package dev.hrishi.sec.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.hrishi.sec.dto.UserDto;
import dev.hrishi.sec.exception.RoleNotAllowedException;
import dev.hrishi.sec.exception.RoleNotFound;
import dev.hrishi.sec.exception.UserAlreadyExistsException;
import dev.hrishi.sec.exception.UserNotFound;
import dev.hrishi.sec.model.Role;
import dev.hrishi.sec.model.User;
import dev.hrishi.sec.repo.RoleRepo;
import dev.hrishi.sec.repo.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public List<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
        .orElseThrow(() -> new UserNotFound("User not found with email: " + email));
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
        .orElseThrow(() -> new UserNotFound("User not found with id: " + id));
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public List<User> getUsersByRole(String roleName) {
        Role role = roleRepo.findByName(roleName);
        return userRepo.findByRole(role);
    }

    public User createUser(UserDto userDto) {
        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + userDto.getEmail());
        }
        Role role = roleRepo.findByName("USER");
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));   
        newUser.setRole(role);
        return userRepo.save(newUser);
    }

    public User createEmployeeUser(UserDto userDto) {
        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + userDto.getEmail());
        }
        if (userDto.getRole().equals("ADMIN")) {
            throw new RoleNotAllowedException("User not allowed to create user with role: " + userDto.getRole());
        }
        Role role = roleRepo.findByName(userDto.getRole());
        if (role == null) {
            throw new RoleNotFound("Role not found: " + userDto.getRole());
        }
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));   
        newUser.setRole(role);
        return userRepo.save(newUser);
    }

    public User updateUser(User user) {
        try {
            User existingUser = getUserById(user.getId());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setRole(user.getRole());
            return userRepo.save(existingUser);
        } catch (UserNotFound e) {
            throw new UserNotFound("User not found with id: " + user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteUser(Long id) {
        try {
            User existingUser = getUserById(id);
            userRepo.delete(existingUser);
            return ResponseEntity.ok("User deleted successfully");
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }          
    }
}
