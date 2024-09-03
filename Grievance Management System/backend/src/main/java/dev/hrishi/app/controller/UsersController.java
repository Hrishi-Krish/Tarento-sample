package dev.hrishi.app.controller;

import dev.hrishi.app.dto.UsersDto;
import dev.hrishi.app.model.Users;
import dev.hrishi.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("users")
    public List<Users> getUsers() {
        return usersService.getUsers();
    }

    @GetMapping("users/{id}")
    public Users getUserById(@PathVariable long id) {
        return usersService.getUserById(id);
    }

    @GetMapping("users/email/{email}")
    public Users getUserByEmail(@PathVariable String email) {
        return usersService.getUserByEmail(email);
    }

    @GetMapping("users/name/{name}")
    public List<Users> getUsersByName(@PathVariable String name) {
        return usersService.getUsersByName(name);
    }

    @GetMapping("users/role/{role}")
    public List<Users> getUsersByRole(@PathVariable String role) {
        return usersService.getUsersByRole(role);
    }

    @PostMapping("users/register")
    public ResponseEntity<String> register(@RequestBody UsersDto usersDto) throws Exception {
        return usersService.register(usersDto);
    }

    @PatchMapping("users/update")
    public ResponseEntity<String> updateUser(@RequestBody UsersDto usersDto) throws Exception {
        return usersService.update(usersDto);
    }

    @DeleteMapping("users/remove")
    public ResponseEntity<String> removeUser(@RequestBody UsersDto usersDto) throws Exception {
        return usersService.remove(usersDto);
    }
}
