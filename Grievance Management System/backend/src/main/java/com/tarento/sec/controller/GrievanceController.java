package dev.hrishi.sec.controller;

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

import dev.hrishi.sec.dto.GrievanceDto;
import dev.hrishi.sec.model.Grievance;
import dev.hrishi.sec.service.GrievanceService;

@RestController
@RequestMapping("/api/grievances")
public class GrievanceController {

    private final GrievanceService grievanceService;

    public GrievanceController(GrievanceService grievanceService) {
        this.grievanceService = grievanceService;
    } 

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<Grievance> getAllGrievances() {
        return grievanceService.getAllGrievances();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public Grievance getGrievanceById(@PathVariable Long id) {
        return grievanceService.getGrievanceById(id);
    }

    @GetMapping("/user/{id}")
    public List<Grievance> getGrievancesByUser(@PathVariable Long id) {
        return grievanceService.getGrievancesByUser(id);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public List<Grievance> getGrievancesByStatus(@PathVariable String status) {
        return grievanceService.getGrievancesByStatus(status);
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public List<Grievance> getGrievancesByCategory(@PathVariable String category) {
        return grievanceService.getGrievancesByCategory(category);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<String> deleteGrievance(@PathVariable Long id) {
        return grievanceService.deleteGrievance(id);
    }

    @PostMapping("/")
    public Grievance createGrievance(@RequestBody GrievanceDto grievanceDto) {
        return grievanceService.createGrievance(grievanceDto);
    }
    
    @PutMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'ASSIGNEE')")
    public Grievance updateGrievanceStatus(@PathVariable Long id, @RequestBody String status) {
        return grievanceService.updateGrievanceStatus(id, status);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public Grievance updateGrievanceCategory(@PathVariable Long id, @RequestBody String category) {
        return grievanceService.updateGrievanceCategory(id, category);
    }
    
}
