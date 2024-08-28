package dev.hrishi.app.controller;

import dev.hrishi.app.dto.AssignmentDto;
import dev.hrishi.app.dto.UsersDto;
import dev.hrishi.app.model.Assignment;
import dev.hrishi.app.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("assignments")
    private List<Assignment> getAssignments() {
        return assignmentService.getAssignments();
    }

    @GetMapping("assignments/{id}")
    private Assignment getAssignmentById(@PathVariable long id) {
        return assignmentService.getAssignmentById(id);
    }

    @GetMapping({"assignments/supervisor", "assignments/assignee"})
    private List<Assignment> getAssignmentByUser(@RequestBody UsersDto usersDto) {
        return assignmentService.getAssignmentByUser(usersDto.getEmail());
    }

    @PostMapping("assignments/new")
    private ResponseEntity<String> newAssignment(@RequestBody AssignmentDto assignmentDto) {
        System.out.println(assignmentDto.getSupervisorId());
        System.out.println(assignmentDto.getAssigneeId());
        System.out.println(assignmentDto.getGrievanceId());
        return assignmentService.newAssignment(assignmentDto);
    }

    @PatchMapping("assignments/update")
    private ResponseEntity<String> updateAssignment(@RequestBody Assignment assignment) {
        return assignmentService.updateAssignment(assignment);
    }

    @DeleteMapping("assignments/delete")
    private ResponseEntity<String> deleteAssignment(@RequestBody Assignment assignment) {
        return assignmentService.deleteAssignment(assignment);
    }
}
