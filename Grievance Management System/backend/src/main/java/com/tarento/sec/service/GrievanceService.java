package com.tarento.sec.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tarento.sec.dto.GrievanceDto;
import com.tarento.sec.exception.GrievanceNotFoundException;
import com.tarento.sec.exception.UserNotFound;
import com.tarento.sec.model.Grievance;
import com.tarento.sec.model.User;
import com.tarento.sec.repo.GrievanceRepo;
import com.tarento.sec.response.grievance.EmployeeGrievanceResponse;
import com.tarento.sec.response.grievance.UserGreivanceResponse;

@Service
public class GrievanceService {

    private final GrievanceRepo grievanceRepo;
    private final UserService userService;

    public GrievanceService(GrievanceRepo grievanceRepo, UserService userService) {
        this.grievanceRepo = grievanceRepo;
        this.userService = userService;
    }

    public List<EmployeeGrievanceResponse> getAllGrievances() {
        try {
            List<Grievance> grievances = grievanceRepo.findAll();
            List<EmployeeGrievanceResponse> employeeGrievanceResponses = new ArrayList<>();
            for (Grievance grievance : grievances) {
                EmployeeGrievanceResponse employeeGrievanceResponse = new EmployeeGrievanceResponse();
                employeeGrievanceResponse.setId(grievance.getId());
                employeeGrievanceResponse.setDescription(grievance.getDescription());
                employeeGrievanceResponse.setTitle(grievance.getTitle());
                employeeGrievanceResponse.setCategory(grievance.getCategory());
                employeeGrievanceResponse.setStatus(grievance.getStatus());
                employeeGrievanceResponse.setUserEmail(grievance.getUser().getEmail());
                employeeGrievanceResponses.add(employeeGrievanceResponse);
            }
            return employeeGrievanceResponses;
        } catch (Exception e) {
            return null;
        }
        
    }

    public EmployeeGrievanceResponse getGrievanceById(Long id) {
        try {
            Grievance grievance = grievanceRepo.findById(id).orElseThrow(() -> new GrievanceNotFoundException("Grievance not found") );
            EmployeeGrievanceResponse employeeGrievanceResponse = new EmployeeGrievanceResponse();
            employeeGrievanceResponse.setId(grievance.getId());
            employeeGrievanceResponse.setTitle(grievance.getTitle());
            employeeGrievanceResponse.setDescription(grievance.getDescription());
            employeeGrievanceResponse.setCategory(grievance.getCategory());
            employeeGrievanceResponse.setStatus(grievance.getStatus());
            employeeGrievanceResponse.setUserEmail(grievance.getUser().getEmail());
            return employeeGrievanceResponse;
        } catch (Exception e) {
            return null;
        }
        

    }

    public ResponseEntity<String> createGrievance(GrievanceDto grievanceDto) {
        try {
            User user = userService.getUserByEmail(grievanceDto.getEmail());
            Grievance grievance = new Grievance(grievanceDto, user);
            grievanceRepo.save(grievance);
            return new ResponseEntity<>("Grievance created successfully", HttpStatus.CREATED);
        } catch (UserNotFound e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Grievance not created", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateGrievanceCategory(Long id, String category) {
        try {
            Grievance grievance = grievanceRepo.findById(id).orElseThrow(() -> new GrievanceNotFoundException("Grievance not found"));
            grievance.setCategory(category);
            grievanceRepo.save(grievance);
            return new ResponseEntity<>("Grievance category updated successfully", HttpStatus.OK);
        } catch (GrievanceNotFoundException e) {
            return new ResponseEntity<>("Grievance not found", HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>("Category not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateGrievanceStatus(Long id) {
        try {
            final String status = "Grievance Resolved";
            Grievance grievance = grievanceRepo.findById(id).orElseThrow(() -> new GrievanceNotFoundException("Grievance not found"));
            grievance.setStatus(status);
            grievanceRepo.save(grievance);
            return new ResponseEntity<>("Grievance status updated successfully", HttpStatus.OK);
        } catch (GrievanceNotFoundException e) {
            return new ResponseEntity<>("Grievance not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Status not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<EmployeeGrievanceResponse> getGrievancesByStatus(String status) {
        try {
            List<Grievance> grievances = grievanceRepo.findByStatus(status);
            List<EmployeeGrievanceResponse> employeeGrievanceResponses = new ArrayList<>();
            for (Grievance grievance : grievances) {
                EmployeeGrievanceResponse employeeGrievanceResponse = new EmployeeGrievanceResponse();
                employeeGrievanceResponse.setId(grievance.getId());
                employeeGrievanceResponse.setTitle(grievance.getTitle());
                employeeGrievanceResponse.setDescription(grievance.getDescription());
                employeeGrievanceResponse.setCategory(grievance.getCategory());
                employeeGrievanceResponse.setStatus(grievance.getStatus());
                employeeGrievanceResponse.setUserEmail(grievance.getUser().getEmail());
                employeeGrievanceResponses.add(employeeGrievanceResponse);
            }
            return employeeGrievanceResponses;    
        } catch (Exception e) {
            return null;
        }
        
    }

    public List<EmployeeGrievanceResponse> getGrievancesByCategory(String category) {
        try {
            List<Grievance> grievances = grievanceRepo.findByCategory(category);
            List<EmployeeGrievanceResponse> employeeGrievanceResponses = new ArrayList<>();
            for (Grievance grievance : grievances) {
                EmployeeGrievanceResponse employeeGrievanceResponse = new EmployeeGrievanceResponse();
                employeeGrievanceResponse.setId(grievance.getId());
                employeeGrievanceResponse.setTitle(grievance.getTitle());
                employeeGrievanceResponse.setDescription(grievance.getDescription());
                employeeGrievanceResponse.setCategory(grievance.getCategory());
                employeeGrievanceResponse.setStatus(grievance.getStatus());
                employeeGrievanceResponse.setUserEmail(grievance.getUser().getEmail());
                employeeGrievanceResponses.add(employeeGrievanceResponse);
            }
            return employeeGrievanceResponses;
        } catch (Exception e) {
            return null;
        }
         
    }
    
    public List<UserGreivanceResponse> getGrievancesByUser(long id) {
        try {
            User user = userService.getUserById(id);
            List<Grievance> grievances = grievanceRepo.findByUser(user);
        List<UserGreivanceResponse> userGrievanceResponses = new ArrayList<>();
        for (Grievance grievance : grievances) {
            UserGreivanceResponse userGrievanceResponse = new UserGreivanceResponse();
            userGrievanceResponse.setId(grievance.getId());
            userGrievanceResponse.setTitle(grievance.getTitle());
            userGrievanceResponse.setDescription(grievance.getDescription());
            userGrievanceResponse.setDescription(grievance.getDescription());
            userGrievanceResponse.setCategory(grievance.getCategory());
            userGrievanceResponse.setStatus(grievance.getStatus());
            userGrievanceResponses.add(userGrievanceResponse);
        }
        return userGrievanceResponses;
        } catch (UserNotFound e) {
            return null;
        }
        
    }

    public ResponseEntity<String> deleteGrievance(Long id) {
        try {
            Grievance grievance = grievanceRepo.findById(id).orElseThrow(() -> new GrievanceNotFoundException("Grievance not found"));
            grievanceRepo.delete(grievance);
            return ResponseEntity.ok().body("Grievance deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Grievance not found", HttpStatus.NOT_FOUND);
        }
    }
}
