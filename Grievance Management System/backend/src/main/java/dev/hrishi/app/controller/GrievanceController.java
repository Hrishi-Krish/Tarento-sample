package dev.hrishi.app.controller;

import dev.hrishi.app.dto.GrievanceDto;
import dev.hrishi.app.model.Grievance;
import dev.hrishi.app.service.GrievanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class GrievanceController {

    @Autowired
    private GrievanceService grievanceService;

    @GetMapping("grievances")
    public List<Grievance> getGrievances() {
        return grievanceService.getGrievances();
    }

    @GetMapping("grievances/{id}")
    public Grievance getGrievanceById(@PathVariable long id) {
        return grievanceService.getGrievanceById(id);
    }

    @GetMapping("grievances/creator")
    public List<Grievance> getGrievanceByCreator(@RequestBody GrievanceDto grievanceDto) {
        return grievanceService.getGrievanceByCreator(grievanceDto.getEmail());
    }

    @PostMapping("grievances/new")
    public ResponseEntity<String> createGrievance(@RequestBody GrievanceDto grievanceDto) {
        return grievanceService.newGrievance(grievanceDto);
    }

    @PatchMapping("grievances/update")
    public ResponseEntity<String> updateGrievance(@RequestBody Grievance grievance) {
        return grievanceService.updateGrievance(grievance);
    }

    @DeleteMapping("grievances/delete")
    public ResponseEntity<String> deleteGrievance(@RequestBody Grievance grievance) {
        return grievanceService.deleteGrievance(grievance);
    }
}
