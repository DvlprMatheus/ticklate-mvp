package com.dvlprmatheus.ticklate.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private LocalDateTime dueDate;

    private boolean notified;
    
    public Task() {}

    public Task(String title, LocalDateTime dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.notified = false;
    }

    // Check if the task is overdue and not notified
    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(dueDate) && !notified;
    }

    // Mark the task as notified
    public void markAsNotified() {
        this.notified = true;
    }

    public UUID getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public boolean isNotified() {
        return notified;
    }
}
