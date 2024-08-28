package dev.hrishi.app.repo;

import dev.hrishi.app.model.Grievance;
import dev.hrishi.app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrievanceRepo extends JpaRepository<Grievance, Long> {
    List<Grievance> findByUser(Users users);
}
