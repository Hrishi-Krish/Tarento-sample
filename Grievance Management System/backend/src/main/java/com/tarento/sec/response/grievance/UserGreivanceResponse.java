package com.tarento.sec.response.grievance;

import lombok.Data;

@Data
public class UserGreivanceResponse {

    private long id;
    private String title;
    private String description;
    private String category;
    private String status;
}
