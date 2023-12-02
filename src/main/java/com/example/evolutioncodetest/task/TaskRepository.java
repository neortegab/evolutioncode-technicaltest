package com.example.evolutioncodetest.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

    List<TaskModel> findByDescriptionContains(String description);

    List<TaskModel> findAllByIsCompleted(Boolean isCompleted);

}
