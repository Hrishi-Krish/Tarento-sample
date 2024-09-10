package com.tarento.sec.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tarento.sec.model.Grievance;
import com.tarento.sec.model.User;

@Repository
public interface GrievanceRepo extends JpaRepository<Grievance, Long>{
    List<Grievance> findByUser(User user);
    List<Grievance> findByStatus(String status);
    List<Grievance> findByCategory(String category);
    
}
