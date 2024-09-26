package com.tarento.sec.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "grievance_id", nullable = false)
    private Grievance grievance;
    @ManyToOne
    @JoinColumn(name = "assigned_by_id", nullable = true)
    private User supervisor;
    @ManyToOne
    @JoinColumn(name = "assigned_to_id", nullable = true)
    private User assignee;
    private LocalDateTime createdAt;
    private LocalDateTime assignedAt;

    public Assignment(Grievance grievance, User supervisor, User assignee) {
        this.grievance = grievance;
        this.supervisor = supervisor;
        this.assignee = assignee;
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.assignedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.assignedAt = LocalDateTime.now();
    }
}
