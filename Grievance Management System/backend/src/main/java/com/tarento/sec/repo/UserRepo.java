package com.tarento.sec.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tarento.sec.model.Role;
import com.tarento.sec.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
}
