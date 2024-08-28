package dev.hrishi.app.repo;

import dev.hrishi.app.model.Assignment;
import dev.hrishi.app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment, Long> {
    List<Assignment> findByAssignedBy(Users user);
    List<Assignment> findByAssignedTo(Users user);
}
