package com.tarento.sec.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarento.sec.dto.AssignmentDto;
import com.tarento.sec.model.Assignment;
import com.tarento.sec.response.assignment.AssignmentResponse;
import com.tarento.sec.service.AssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentService.getAllAssignmentDetails();
    }

    @GetMapping("/raw/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<Assignment> getAllAssignmentsRaw() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public AssignmentResponse getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentDetailsById(id);
    }

    @GetMapping("/raw/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public Assignment getAllAssignmentsByIdRaw(@PathVariable long id) {
        return assignmentService.getAssignmentById(id);
    }

    @GetMapping("/grievance/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public AssignmentResponse getAssignmentByGrievanceId(@PathVariable Long id) {
        return assignmentService.getAssignmentDetailsByGrievanceId(id);
    }

    @GetMapping("/raw/grievance/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public Assignment getAllAssignmentsByGrievanceIdRaw(@PathVariable long id) {
        return assignmentService.getAssignmentByGrievanceId(id);
    }

    @GetMapping("/supervisor/{supervisorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<AssignmentResponse> getAssignmentsBySupervisor(@PathVariable Long supervisorId) {
        return assignmentService.getAssignmentDetailsBySupervisor(supervisorId);
    }

    @GetMapping("/assignee/{assigneeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public List<AssignmentResponse> getAssignmentsByAssignee(@PathVariable Long assigneeId) { 
        return assignmentService.getAssignmentDetailsByAssignee(assigneeId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        return assignmentService.createAssignment(assignmentDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {
        return assignmentService.updateAssignment(id, assignmentDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id) {
        return assignmentService.deleteAssignment(id);
    }
}
