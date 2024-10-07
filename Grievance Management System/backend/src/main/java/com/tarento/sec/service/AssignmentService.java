package com.tarento.sec.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tarento.sec.dto.AssignmentDto;
import com.tarento.sec.exception.UserAlreadyExistsException;
import com.tarento.sec.exception.UserNotFound;
import com.tarento.sec.model.Assignment;
import com.tarento.sec.model.Grievance;
import com.tarento.sec.model.User;
import com.tarento.sec.repo.AssignmentRepo;
import com.tarento.sec.repo.GrievanceRepo;
import com.tarento.sec.repo.UserRepo;
import com.tarento.sec.response.assignment.AssignmentResponse;

@Service
public class AssignmentService {

    private final AssignmentRepo assignmentRepo;
    private final UserRepo userRepo;
    private final GrievanceRepo grievanceRepo;

    public AssignmentService(AssignmentRepo assignmentRepo, UserRepo userRepo, GrievanceRepo grievanceRepo) {
        this.assignmentRepo = assignmentRepo;
        this.userRepo = userRepo;
        this.grievanceRepo = grievanceRepo;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepo.findAll();
    }

    public List<AssignmentResponse> getAllAssignmentDetails() {
        try {
            List<Assignment> assignments = assignmentRepo.findAll();
            List<AssignmentResponse> assignmentResponses = new ArrayList<>();
            for (Assignment assignment : assignments) {
                AssignmentResponse assignmentResponse = new AssignmentResponse();
                assignmentResponse.setId(assignment.getId());
                assignmentResponse.setTitle(assignment.getGrievance().getTitle());
                assignmentResponse.setDescription(assignment.getGrievance().getDescription());
                assignmentResponse.setCategory(assignment.getGrievance().getCategory());
                assignmentResponse.setStatus(assignment.getGrievance().getStatus());
                assignmentResponse.setAssigneeEmail(assignment.getAssignee().getEmail());
                assignmentResponse.setSupervisorEmail(assignment.getSupervisor().getEmail());
                assignmentResponses.add(assignmentResponse);
            }
            return assignmentResponses;
        } catch (Exception e) {
            return null;
        }
    }

    public Assignment getAssignmentById(Long id) {
        return assignmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public AssignmentResponse getAssignmentDetailsById(long id) {
        try {
            Assignment assignment = assignmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
            AssignmentResponse assignmentResponse = new AssignmentResponse();
            assignmentResponse.setId(assignment.getId());
            assignmentResponse.setTitle(assignment.getGrievance().getTitle());
            assignmentResponse.setDescription(assignment.getGrievance().getDescription());
            assignmentResponse.setCategory(assignment.getGrievance().getCategory());
            assignmentResponse.setStatus(assignment.getGrievance().getStatus());
            assignmentResponse.setAssigneeEmail(assignment.getAssignee().getEmail());
            assignmentResponse.setSupervisorEmail(assignment.getSupervisor().getEmail());
            return assignmentResponse;
        } catch (Exception e) {
            return null;
        }
    }

    public AssignmentResponse getAssignmentDetailsByGrievanceId(long id) {
        try {
            Assignment assignment = assignmentRepo.findByGrievanceId(id);
            AssignmentResponse assignmentResponse = new AssignmentResponse();
            assignmentResponse.setId(assignment.getId());
            assignmentResponse.setTitle(assignment.getGrievance().getTitle());
            assignmentResponse.setDescription(assignment.getGrievance().getDescription());
            assignmentResponse.setCategory(assignment.getGrievance().getCategory());
            assignmentResponse.setStatus(assignment.getGrievance().getStatus());
            assignmentResponse.setAssigneeEmail(assignment.getAssignee().getEmail());
            assignmentResponse.setSupervisorEmail(assignment.getSupervisor().getEmail());
            return assignmentResponse;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Assignment getAssignmentByGrievanceId(long id) {
        return assignmentRepo.findByGrievanceId(id);
    }

    public ResponseEntity<String> createAssignment(AssignmentDto assignmentDto) {
        try {
            if (assignmentRepo.findByGrievanceId(assignmentDto.getGrievanceId()) != null) {
                throw new UserAlreadyExistsException("Already assigned");
            }
            User supervisor = userRepo.findById(assignmentDto.getSupervisorId()).orElseThrow(() -> new RuntimeException("Supervisor not found"));
            User assignee = userRepo.findById(assignmentDto.getAssigneeId()).orElseThrow(() -> new RuntimeException("Assignee not found"));
            Grievance grievance = grievanceRepo.findById(assignmentDto.getGrievanceId()).orElseThrow(() -> new RuntimeException("Grievance not found"));
            grievance.setStatus("Assigned for Resolution");
            Assignment assignment = new Assignment(grievance, supervisor, assignee);
            assignmentRepo.save(assignment);
            return new ResponseEntity<>("Assignment created successfully", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>("Already Assigned", HttpStatus.CONFLICT);
        }
         catch(Exception e) {
            return new ResponseEntity<>("Grievance not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> updateAssignment(Long id, AssignmentDto assignmentDto) {
        try {
            Assignment assignment = assignmentRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Assignment not found"));
            assignment.setSupervisor(userRepo.findById(assignmentDto.getSupervisorId())
                    .orElseThrow(() -> new RuntimeException("Supervisor not found")));
            assignment.setAssignee(userRepo.findById(assignmentDto.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found")));
            assignmentRepo.save(assignment);
            return new ResponseEntity<>("Assignment updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Assignment not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    public List<Assignment> getAssignmentsBySupervisor(Long supervisorId) {
        try {
            User supervisor = userRepo.findById(supervisorId)
                    .orElseThrow(() -> new UserNotFound("Supervisor not found"));
            return assignmentRepo.findBySupervisor(supervisor);
        } catch (UserNotFound e) {
            return null;
        }
    }

    public List<AssignmentResponse> getAssignmentDetailsBySupervisor(long supervisorId) {
        try {
            User supervisor = userRepo.findById(supervisorId).orElseThrow(() -> new UserNotFound("Supervisor not found"));
            List<Assignment> assignments = assignmentRepo.findBySupervisor(supervisor);
            List<AssignmentResponse> assignmentResponses = new ArrayList<>();
            for (Assignment assignment : assignments) {
                AssignmentResponse assignmentResponse = new AssignmentResponse();
                assignmentResponse.setId(assignment.getId());
                assignmentResponse.setTitle(assignment.getGrievance().getTitle());
                assignmentResponse.setDescription(assignment.getGrievance().getDescription());
                assignmentResponse.setCategory(assignment.getGrievance().getCategory());
                assignmentResponse.setStatus(assignment.getGrievance().getStatus());
                assignmentResponse.setAssigneeEmail(assignment.getAssignee().getEmail());
                assignmentResponse.setSupervisorEmail(assignment.getSupervisor().getEmail());
                assignmentResponses.add(assignmentResponse);
            }
            return assignmentResponses;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Assignment> getAssignmentsByAssignee(Long assigneeId) {
        try {
            User assignee = userRepo.findById(assigneeId).orElseThrow(() -> new UserNotFound("Assignee not found"));
            return assignmentRepo.findByAssignee(assignee);
        } catch (UserNotFound e) {
            return null;
        }
    }

    public List<AssignmentResponse> getAssignmentDetailsByAssignee(long assigneeId) {
        try {
            User assignee = userRepo.findById(assigneeId).orElseThrow(() -> new UserNotFound("Assignee not found"));
            List<Assignment> assignments = assignmentRepo.findByAssignee(assignee);
            List<AssignmentResponse> assignmentResponses = new ArrayList<>();
            for (Assignment assignment : assignments) {
                if (assignment.getGrievance().getStatus().equals("Grievance Resolved")) continue;
                AssignmentResponse assignmentResponse = new AssignmentResponse();
                assignmentResponse.setId(assignment.getGrievance().getId());
                assignmentResponse.setTitle(assignment.getGrievance().getTitle());
                assignmentResponse.setDescription(assignment.getGrievance().getDescription());
                assignmentResponse.setCategory(assignment.getGrievance().getCategory());
                assignmentResponse.setStatus(assignment.getGrievance().getStatus());
                assignmentResponse.setAssigneeEmail(assignment.getAssignee().getEmail());
                assignmentResponse.setSupervisorEmail(assignment.getSupervisor().getEmail());
                assignmentResponses.add(assignmentResponse);
            }
            return assignmentResponses;
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<String> deleteAssignment(Long id) {
        try {
            Assignment assignment = assignmentRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Assignment not found"));
            assignmentRepo.delete(assignment);
            return ResponseEntity.ok("Assignment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
