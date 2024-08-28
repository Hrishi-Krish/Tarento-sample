package dev.hrishi.app.repo;

import dev.hrishi.app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    public Users findByEmail(String email);
    public Users findByUsername(String username);
}
