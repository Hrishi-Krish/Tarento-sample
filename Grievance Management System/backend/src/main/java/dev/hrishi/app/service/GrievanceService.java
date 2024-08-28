package dev.hrishi.app.service;

import dev.hrishi.app.dto.GrievanceDto;
import dev.hrishi.app.model.Grievance;
import dev.hrishi.app.model.Users;
import dev.hrishi.app.repo.GrievanceRepo;
import dev.hrishi.app.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrievanceService {

    @Autowired
    private GrievanceRepo grievanceRepo;

    @Autowired
    private UsersRepo usersRepo;

    public List<Grievance> getGrievances() {
        return grievanceRepo.findAll();
    }

    public Grievance getGrievanceById(long id) {
        Optional<Grievance> grievance = grievanceRepo.findById(id);
        if (grievance.isEmpty()) {
            throw new RuntimeException("Grievance with id " + id + " not found");
        }
        return grievance.get();
    }

    public List<Grievance> getGrievanceByCreator(String email) {
        try {
            Users user = usersRepo.findByEmail(email);
            return grievanceRepo.findByUser(user);
        } catch (Exception e) {
            return null;
        }

    }

    public ResponseEntity<String> newGrievance(GrievanceDto grievanceDto) {
        try {
            Users users = usersRepo.findByEmail(grievanceDto.getEmail());
            Grievance grievance = new Grievance(grievanceDto, users);
            grievanceRepo.save(grievance);
            return new ResponseEntity<>("Grievance created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Grievance not created.\nSomething went wrong", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<String> updateGrievance(Grievance grievance) {
        try {
            Grievance existingGrievance = getGrievanceById(grievance.getId());
            existingGrievance.setCategory(grievance.getCategory());
            existingGrievance.setStatus(grievance.getStatus());
            grievanceRepo.save(existingGrievance);
            return new ResponseEntity<>("Grievance updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("update unsuccessful", HttpStatus.NOT_MODIFIED);
        }
    }

    public ResponseEntity<String> deleteGrievance(Grievance grievance) {
        try {
            Grievance req = getGrievanceById(grievance.getId());
            grievanceRepo.delete(req);
            return new ResponseEntity<>("Grievance deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deletion unsuccessful", HttpStatus.NOT_MODIFIED);
        }
    }

}
