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
    private final RoleRepo roleRepo;

    public AdminInitialiser(@Lazy UserService userService, RoleRepo roleRepo) {
        this.userService = userService;
        this.roleRepo = roleRepo;
    }

    @PostConstruct
    public void init() {
        if (roleRepo.findByName("ADMIN") == null) {
            roleRepo.save(new Role("ADMIN"));
        }
        if (roleRepo.findByName("USER") == null) {
            roleRepo.save(new Role("USER"));
        }
        if (roleRepo.findByName("SUPERVISOR") == null) {
            roleRepo.save(new Role("SUPERVISOR"));
        }
        if (roleRepo.findByName("ASSIGNEE") == null) {
            roleRepo.save(new Role("ASSIGNEE"));
        }

        if (!isCreated) {
            try {
                if (userService.getUserByEmail("admin@dev.com") != null) {
                    isCreated = true;
                    return;
                }
            } catch (UserNotFound e) {
                UserDto userDto = new UserDto("admin", "admin@dev.com", "admin@123", "ADMIN");
                userService.createAdmin(userDto);
                isCreated = true;
            } catch (Exception e) {
                isCreated = true;
            }
        }
    }
}
