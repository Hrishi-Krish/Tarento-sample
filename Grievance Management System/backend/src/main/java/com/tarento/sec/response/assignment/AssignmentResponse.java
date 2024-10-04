package com.tarento.sec.response.assignment;

import lombok.Data;

@Data
public class AssignmentResponse {
    
    private long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private String supervisorEmail;
    private String assigneeEmail;
}
