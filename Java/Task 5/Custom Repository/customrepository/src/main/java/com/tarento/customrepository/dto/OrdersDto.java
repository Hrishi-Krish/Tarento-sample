package com.tarento.customrepository.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrdersDto {
    private LocalDateTime orderDate;
    private String status;
}
