package dev.hrishi.sec.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.hrishi.sec.model.Assignment;
import dev.hrishi.sec.model.User;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment, Long> {
    List<Assignment> findBySupervisor(User user);
    List<Assignment> findByAssignee(User user);
}
