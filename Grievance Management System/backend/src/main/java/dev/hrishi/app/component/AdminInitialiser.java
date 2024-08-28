package dev.hrishi.app.component;

import dev.hrishi.app.dto.UsersDto;
import dev.hrishi.app.exception.UserWithGivenEmailNotFound;
import dev.hrishi.app.service.UsersService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInitialiser {

    boolean isCreated = false;

    @Autowired
    private UsersService usersService;

    @PostConstruct
    public void createAdmin() throws Exception {
        if (isCreated) {
            return;
        }
        try {
            usersService.getUserByEmail("admin@dev.com");
            isCreated = true;
            return;
        } catch (UserWithGivenEmailNotFound e) {
            UsersDto users = new UsersDto("admin", "admin@123", "admin@dev.com", "ADMIN");
            usersService.register(users);
        }

    }

}
