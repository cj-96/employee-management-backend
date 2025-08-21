package org.example.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
@Filter(name = "softDeleteFilter", condition = "deleted_at IS NULL")
public class TaskEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatusEnum status;

    @Column()
    private Date dueDate;

    // Relationship: Assigned to Employee
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private EmployeeEntity assignedTo;

    // Relationship: Created by Employee
    @ManyToOne
    @JoinColumn(updatable = false, referencedColumnName = "id")
    private EmployeeEntity createdBy;

    // Timestamps
    @LastModifiedDate
    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    @CreatedDate
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(updatable = false)
    private Date deletedAt;

    // Tracking users who update or delete the task (if required)
    @Column()
    private Long updatedBy;

    @Column()
    private Long deletedBy;
}
