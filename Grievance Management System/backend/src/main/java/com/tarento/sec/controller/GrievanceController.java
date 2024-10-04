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

import com.tarento.sec.dto.GrievanceDto;
import com.tarento.sec.response.grievance.EmployeeGrievanceResponse;
import com.tarento.sec.response.grievance.UserGreivanceResponse;
import com.tarento.sec.service.GrievanceService;

@RestController
@RequestMapping("/api/grievances")
public class GrievanceController {

    private final GrievanceService grievanceService;

    public GrievanceController(GrievanceService grievanceService) {
        this.grievanceService = grievanceService;
    } 

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<EmployeeGrievanceResponse> getAllGrievances() {
        return grievanceService.getAllGrievances();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public EmployeeGrievanceResponse getGrievanceById(@PathVariable Long id) {
        return grievanceService.getGrievanceById(id);
    }

    @GetMapping("/user/{id}")
    public List<UserGreivanceResponse> getGrievancesByUser(@PathVariable Long id) {
        return grievanceService.getGrievancesByUser(id);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public List<EmployeeGrievanceResponse> getGrievancesByStatus(@PathVariable String status) {
        return grievanceService.getGrievancesByStatus(status);
    }

    @GetMapping("/category")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<EmployeeGrievanceResponse> getGrievancesByCategory(@RequestBody String category) {
        return grievanceService.getGrievancesByCategory(category);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> deleteGrievance(@PathVariable Long id) {
        return grievanceService.deleteGrievance(id);
    }

    @PostMapping("/")
    public ResponseEntity<String> createGrievance(@RequestBody GrievanceDto grievanceDto) {
        return grievanceService.createGrievance(grievanceDto);
    }
    
    @PutMapping("/resolved/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public ResponseEntity<String> updateGrievanceStatus(@PathVariable Long id) {
        return grievanceService.updateGrievanceStatus(id);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> updateGrievanceCategory(@PathVariable Long id, @RequestBody String category) {
        return grievanceService.updateGrievanceCategory(id, category);
    }
}
