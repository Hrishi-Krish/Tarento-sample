package dev.hrishi.sec.component;

import org.springframework.stereotype.Component;

import dev.hrishi.sec.model.Role;
import dev.hrishi.sec.repo.RoleRepo;
import jakarta.annotation.PostConstruct;

@Component
public class RoleInit {

    private final RoleRepo roleRepo;

    public RoleInit(RoleRepo roleRepo) {
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
    }
        
}
