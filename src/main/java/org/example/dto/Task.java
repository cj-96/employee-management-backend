package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.entity.PriorityEnum;
import org.example.entity.TaskStatusEnum;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {

    private Long id;
    private String title;
    private String description;
    private PriorityEnum priority;
    private TaskStatusEnum status;
    private LocalDate dueDate;

    // Employee relations
    private Employee assignedTo;
    private Employee createdBy;

    // Timestamps
    private LocalDate updatedAt;
    private LocalDate createdAt;
    private LocalDate deletedAt;

    // User tracking IDs
    private Long updatedBy;
    private Long deletedBy;
}
