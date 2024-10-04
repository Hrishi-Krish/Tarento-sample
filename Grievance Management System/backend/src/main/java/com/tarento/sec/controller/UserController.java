package com.tarento.sec.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarento.sec.dto.UserDto;
import com.tarento.sec.model.User;
import com.tarento.sec.response.user.UserResponse;
import com.tarento.sec.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    } 

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserDetailsById(id);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<UserResponse> getUserByUsername(@PathVariable String username) {
        return userService.getUserDetailsByUsername(username);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserDetailsByEmail(email);
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<UserResponse> getUsersByRole(@PathVariable String role) {
        return userService.getUserDetailsByRole(role);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<String> updateUserPassword(@RequestBody User user) {
        return userService.updateUserPassword(user);
    }

    @PutMapping("/username/{id}")
    public ResponseEntity<String> updateUsername(@RequestBody User user) {
        return userService.updateUsername(user);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping("/newEmployee")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> createEmployeeUser(@RequestBody UserDto userDto) {
        return userService.createEmployeeUser(userDto);
    }

    @PostMapping("/roleChange")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> changeUserRole(@RequestBody UserDto userDto) {
        return userService.changeUserRole(userDto);
    }
}
