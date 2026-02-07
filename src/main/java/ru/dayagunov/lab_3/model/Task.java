package ru.dayagunov.lab_3.model;

import lombok.*;

@Data
public class Task {
    private final long id;
    private String description;
    private boolean completed;

    public Task(long id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }
}