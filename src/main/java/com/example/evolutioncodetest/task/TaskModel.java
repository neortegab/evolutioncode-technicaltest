package com.example.evolutioncodetest.task;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Nonnull
    private String description;

    private Boolean isCompleted = false;

    public TaskModel(String description){
        this.id = UUID.randomUUID();
        this.description = description;
        this.isCompleted = false;
    }

    public TaskModel(String description, Boolean isCompleted){
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public TaskModel(UUID id, String description, Boolean isCompleted){
        this.id = id;
        this.description = description;
        this.isCompleted = isCompleted;
    }

}
