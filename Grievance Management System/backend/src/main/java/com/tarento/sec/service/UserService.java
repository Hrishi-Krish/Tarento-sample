package com.tarento.sec.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tarento.sec.dto.UserDto;
import com.tarento.sec.exception.RoleNotAllowedException;
import com.tarento.sec.exception.RoleNotFound;
import com.tarento.sec.exception.UserAlreadyExistsException;
import com.tarento.sec.exception.UserNotFound;
import com.tarento.sec.model.Role;
import com.tarento.sec.model.User;
import com.tarento.sec.repo.GrievanceRepo;
import com.tarento.sec.repo.RoleRepo;
import com.tarento.sec.repo.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final GrievanceRepo grievanceRepo;
    
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo, GrievanceRepo grievanceRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.grievanceRepo = grievanceRepo;
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

    public ResponseEntity<String> createUser(UserDto userDto) {
        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + userDto.getEmail());
        }
        Role role = roleRepo.findByName("USER");
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));   
        newUser.setRole(role);
        userRepo.save(newUser);
        return new ResponseEntity<>("New user created succesfully", HttpStatus.OK);
    }

    public ResponseEntity<String> createEmployeeUser(UserDto userDto) {
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
        userRepo.save(newUser);
        return new ResponseEntity<>("User with role " + role + " created", HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(User user) {
        try {
            User existingUser = getUserById(user.getId());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(existingUser);
            return new ResponseEntity<>("User Details modified successfully", HttpStatus.OK);
        } catch (UserNotFound e) {
            throw new UserNotFound("User not found with id: " + user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteUser(Long id) {
        try {
            User existingUser = getUserById(id);
            grievanceRepo.findByUser(existingUser).forEach(grievance -> {
                grievance.setUser(null);
                grievanceRepo.save(grievance);
            });
            userRepo.delete(existingUser);
            return ResponseEntity.ok("User deleted successfully");
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }          
    }

    public ResponseEntity<String> changeUserRole(UserDto userDto) {
        try {
            User existingUser = getUserByEmail(userDto.getEmail());
            Role role = roleRepo.findByName(userDto.getRole());
            existingUser.setRole(role);
            return new ResponseEntity<>("User role changed successfully", HttpStatus.OK);
            
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
