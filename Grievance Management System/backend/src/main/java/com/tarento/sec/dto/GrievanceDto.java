package com.tarento.sec.dto;

import lombok.Data;

@Data
public class GrievanceDto {
    private String title;
    private String description;
    private String email;
    private String category;
    private String status;

}
