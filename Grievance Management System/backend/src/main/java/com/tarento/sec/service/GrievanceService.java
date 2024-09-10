package com.tarento.sec.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tarento.sec.dto.GrievanceDto;
import com.tarento.sec.exception.GrievanceNotFoundException;
import com.tarento.sec.exception.UserNotFound;
import com.tarento.sec.model.Grievance;
import com.tarento.sec.model.User;
import com.tarento.sec.repo.GrievanceRepo;

@Service
public class GrievanceService {

    private final GrievanceRepo grievanceRepo;
    private final UserService userService;

    public GrievanceService(GrievanceRepo grievanceRepo, UserService userService) {
        this.grievanceRepo = grievanceRepo;
        this.userService = userService;
    }

    public List<Grievance> getAllGrievances() {
        return grievanceRepo.findAll();
    }

    public Grievance getGrievanceById(Long id) {
        return grievanceRepo.findById(id).orElseThrow(() -> new GrievanceNotFoundException("Grievance not found"));
    }

    public Grievance createGrievance(GrievanceDto grievanceDto) {
        try {
            User user = userService.getUserByEmail(grievanceDto.getEmail());
            Grievance grievance = new Grievance(grievanceDto, user);
            return grievanceRepo.save(grievance);
        } catch (UserNotFound e) {
            throw new GrievanceNotFoundException("User with email " + grievanceDto.getEmail() + " not found");
        } catch (Exception e) {
            throw new GrievanceNotFoundException("Grievance not found");
        }
    }

    public Grievance updateGrievanceCategory(Long id, String category) {
        try {
            Grievance grievance = grievanceRepo.findById(id).orElseThrow(() -> new GrievanceNotFoundException("Grievance not found"));
            grievance.setCategory(category);
            return grievanceRepo.save(grievance);
        } catch (GrievanceNotFoundException e) {
            throw new GrievanceNotFoundException("Grievance not found");
        }
    }

    public Grievance updateGrievanceStatus(Long id, String status) {
        try {
            Grievance grievance = grievanceRepo.findById(id).orElseThrow(() -> new GrievanceNotFoundException("Grievance not found"));
            grievance.setStatus(status);
            return grievanceRepo.save(grievance);
        } catch (GrievanceNotFoundException e) {
            throw new GrievanceNotFoundException("Grievance not found");
        } 
    }

    public List<Grievance> getGrievancesByStatus(String status) {
        return grievanceRepo.findByStatus(status);
    }

    public List<Grievance> getGrievancesByCategory(String category) {
        return grievanceRepo.findByCategory(category);
    }
    
    public List<Grievance> getGrievancesByUser(long id) {
        User user = userService.getUserById(id);
        return grievanceRepo.findByUser(user);
    }

    public ResponseEntity<String> deleteGrievance(Long id) {
        Grievance grievance = grievanceRepo.findById(id).orElseThrow(() -> new GrievanceNotFoundException("Grievance not found"));
        grievanceRepo.delete(grievance);
        return ResponseEntity.ok().body("Grievance deleted successfully");
    }   
}
