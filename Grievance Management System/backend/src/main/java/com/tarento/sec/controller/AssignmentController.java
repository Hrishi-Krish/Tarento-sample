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
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public Assignment getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }

    @GetMapping("/supervisor/{supervisorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<Assignment> getAssignmentsBySupervisor(@PathVariable Long supervisorId) {
        return assignmentService.getAssignmentsBySupervisor(supervisorId);
    }

    @GetMapping("/assignee/{assigneeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public List<Assignment> getAssignmentsByAssignee(@PathVariable Long assigneeId) { 
        return assignmentService.getAssignmentsByAssignee(assigneeId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public Assignment createAssignment(@RequestBody AssignmentDto assignmentDto) {
        return assignmentService.createAssignment(assignmentDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public Assignment updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {
        return assignmentService.updateAssignment(id, assignmentDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id) {
        return assignmentService.deleteAssignment(id);
    }
    
}
