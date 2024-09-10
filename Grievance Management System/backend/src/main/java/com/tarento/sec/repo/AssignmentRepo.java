package com.tarento.sec.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tarento.sec.model.Assignment;
import com.tarento.sec.model.User;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment, Long> {
    List<Assignment> findBySupervisor(User user);
    List<Assignment> findByAssignee(User user);
}
