package dev.hrishi.app.service;

import dev.hrishi.app.dto.AssignmentDto;
import dev.hrishi.app.model.Assignment;
import dev.hrishi.app.model.Grievance;
import dev.hrishi.app.model.Users;
import dev.hrishi.app.repo.AssignmentRepo;
import dev.hrishi.app.repo.GrievanceRepo;
import dev.hrishi.app.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private GrievanceRepo grievanceRepo;

    @Autowired
    private AssignmentRepo assignmentRepo;

    public List<Assignment> getAssignments() {
        return assignmentRepo.findAll();
    }

    public Assignment getAssignmentById(long id) {
        Optional<Assignment> assignment = assignmentRepo.findById(id);
        return assignment.orElse(null);
    }

    public List<Assignment> getAssignmentByUser(String email) {
        try {
            Users user = usersRepo.findByEmail(email);
            if (user.getRole().equals("SUPERVISOR") || user.getRole().equals("ADMIN"))
                return assignmentRepo.findByAssignedBy(user);
            else if (user.getRole().equals("ASSIGNEE")) {
                return assignmentRepo.findByAssignedTo(user);
            }
            else throw new RuntimeException("User is not an employee");
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<String> newAssignment(AssignmentDto assignmentDto) {
        try {
            Optional<Grievance> grievance = grievanceRepo.findById(assignmentDto.getGrievanceId());
            Optional<Users> supervisor = usersRepo.findById(assignmentDto.getSupervisorId());
            Optional<Users> assignee = usersRepo.findById(assignmentDto.getAssigneeId());
            if (grievance.isEmpty() || supervisor.isEmpty() || assignee.isEmpty()) {
                throw new RuntimeException();
            }
            Assignment assignment = new Assignment(grievance.get(), supervisor.get(), assignee.get());
            assignmentRepo.save(assignment);
            grievance.get().setStatus("");
            return new ResponseEntity<>("Grievance assigned successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Exception\n" + e);
            return new ResponseEntity<>("Assignment creation failed", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateAssignment(Assignment assignment) {
        try {
            Assignment existingAssignment = getAssignmentById(assignment.getId());
            existingAssignment.setAssignedBy(assignment.getAssignedBy());
            existingAssignment.setAssignedTo(assignment.getAssignedTo());
            assignmentRepo.save(existingAssignment);
            return new ResponseEntity<>("Assignment updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Update failed", HttpStatus.NOT_MODIFIED);
        }
    }

    public ResponseEntity<String> deleteAssignment(Assignment assignment) {
        try {
            assignmentRepo.deleteById(assignment.getId());
            return new ResponseEntity<>("Assignment deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deletion unsuccessful", HttpStatus.NOT_MODIFIED);
        }
    }
}
