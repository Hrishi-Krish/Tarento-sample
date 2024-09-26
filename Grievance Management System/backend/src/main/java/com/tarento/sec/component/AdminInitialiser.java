package com.tarento.sec.component;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.tarento.sec.dto.UserDto;
import com.tarento.sec.exception.UserNotFound;
import com.tarento.sec.service.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class AdminInitialiser {

    Boolean isCreated = false;

    private final UserService userService;

    public AdminInitialiser(@Lazy UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void adminInit() {
        if (!isCreated) {
            try {
                if (userService.getUserByEmail("admin@dev.com") != null) {
                    isCreated = true;
                    return;
                }
            } catch (UserNotFound e) {
                UserDto userDto = new UserDto("admin", "admin@dev.com", "admin@123", "ADMIN");
                userService.createUser(userDto);
                isCreated = true;
            } catch (Exception e) {
                isCreated = true;
            }
        }
    }
}
