package dev.hrishi.sec.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.hrishi.sec.model.Grievance;
import dev.hrishi.sec.model.User;

@Repository
public interface GrievanceRepo extends JpaRepository<Grievance, Long>{
    List<Grievance> findByUser(User user);
    List<Grievance> findByStatus(String status);
    List<Grievance> findByCategory(String category);
    
}
