package dev.hrishi.sec.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hrishi.sec.dto.AssignmentDto;
import dev.hrishi.sec.model.Assignment;
import dev.hrishi.sec.service.AssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }

    @GetMapping("/supervisor/{supervisorId}")
    public List<Assignment> getAssignmentsBySupervisor(@PathVariable Long supervisorId) {
        return assignmentService.getAssignmentsBySupervisor(supervisorId);
    }

    @GetMapping("/assignee/{assigneeId}")
    public List<Assignment> getAssignmentsByAssignee(@PathVariable Long assigneeId) { 
        return assignmentService.getAssignmentsByAssignee(assigneeId);
    }

    @PostMapping
    public Assignment createAssignment(@RequestBody AssignmentDto assignmentDto) {
        return assignmentService.createAssignment(assignmentDto);
    }

    @PutMapping("/{id}")
    public Assignment updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {
        return assignmentService.updateAssignment(id, assignmentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id) {
        return assignmentService.deleteAssignment(id);
    }
    
}
