package com.tarento.customrepository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime orderDate;
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    public Orders() {
        super();
    }

    public Orders(LocalDateTime orderDate, String status) {
        this.orderDate = orderDate;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
