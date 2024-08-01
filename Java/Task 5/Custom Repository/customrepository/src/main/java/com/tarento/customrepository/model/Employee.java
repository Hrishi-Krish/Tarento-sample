package com.tarento.customrepository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String dept;

    @Column(name = "hire_date",nullable = false, updatable = false)
    private LocalDate hireDate;

    @PrePersist
    protected void onCreate() {
        this.hireDate = LocalDate.now();
    }

    public Employee() {
        super();
    }

    public Employee(String name, String dept) {
        this.name = name;
        this.dept = dept;
    }


}
