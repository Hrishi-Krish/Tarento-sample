package dev.hrishi.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "assigned_by_id", nullable = false)
    private Users assignedBy;
    @ManyToOne
    @JoinColumn(name = "assigned_to_id", nullable = false)
    private Users assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime assignedAt;

    public Assignment(Grievance grievance, Users supervisor, Users assignee) {
        this.grievance = grievance;
        this.assignedBy = supervisor;
        this.assignedTo = assignee;
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
