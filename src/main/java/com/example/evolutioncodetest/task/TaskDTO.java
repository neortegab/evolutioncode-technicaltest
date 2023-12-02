package com.example.evolutioncodetest.task;

import lombok.Data;

@Data
public class TaskDTO {
    private String description;

    private Boolean isCompleted = false;
}
