package dev.hrishi.sec.dto;

import lombok.Data;

@Data
public class AssignmentDto {
    private long supervisorId; 
    private long assigneeId; 
    private long grievanceId; 
    private long assignmentId; 
}
