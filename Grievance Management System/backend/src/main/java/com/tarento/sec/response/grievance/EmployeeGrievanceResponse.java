package com.tarento.sec.response.grievance;

import lombok.Data;

@Data
public class EmployeeGrievanceResponse {
    
    private long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private String userEmail;
}
