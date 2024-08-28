package dev.hrishi.app.service;

import dev.hrishi.app.dto.UsersDto;
import dev.hrishi.app.exception.UserWithGivenEmailNotFound;
import dev.hrishi.app.model.Users;
import dev.hrishi.app.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<Users> getUsers() {
        return usersRepo.findAll();
    }

    public Users getUserById(long id) {
        Optional<Users> user = usersRepo.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        return user.get();
    }

    public Users getUserByEmail(String email) {
        Users user = usersRepo.findByEmail(email);
        if (user == null) {
            throw new UserWithGivenEmailNotFound("User with email " + email + " not found");
        }
        return user;
    }

    public ResponseEntity<String> register(UsersDto user) throws Exception {
        try {
            Users existingUser = getUserByEmail(user.getEmail());
            return new ResponseEntity<>("User with given email already exists", HttpStatus.CONFLICT);
        } catch (UserWithGivenEmailNotFound e) {
            user.setPassword(encoder.encode(user.getPassword()));
            Users newUser = new Users(user.getUsername(),user.getPassword(),user.getEmail(),user.getRole());
            usersRepo.save(newUser);
            return new ResponseEntity<>("New User created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception("Something went wrong\n" + e);
        }
    }

    public ResponseEntity<String> update(UsersDto user) throws Exception {
        try {
            Users existingUser = getUserByEmail(user.getEmail());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(encoder.encode(user.getPassword()));
            usersRepo.save(existingUser);
            return new ResponseEntity<>("User details updated successfully", HttpStatus.OK);
        } catch (UserWithGivenEmailNotFound e) {
            return new ResponseEntity<>("User with given email does not exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new Exception("Something went wrong\n" + e);
        }
    }

    public ResponseEntity<String> remove(UsersDto user) throws Exception {
        try {
            Users existingUser = getUserByEmail(user.getEmail());
            usersRepo.delete(existingUser);
            return new ResponseEntity<>("User removed successfully", HttpStatus.OK);
        } catch (UserWithGivenEmailNotFound e) {
            return new ResponseEntity<>("User with given email does not exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new Exception("Something went wrong\n" + e);
        }
    }
}
