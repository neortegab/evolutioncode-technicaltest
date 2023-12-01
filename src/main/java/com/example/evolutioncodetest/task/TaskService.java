package com.example.evolutioncodetest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public List<TaskModel> findAll(){
        return repository.findAll();
    }

    public TaskModel findById(UUID id){
        return repository.findById(id).orElseThrow();
    }

    public List<TaskModel> findByDescription(String description){
        return repository.findByDescriptionContains(description);
    }

    public List<TaskModel> findByStatus(boolean isCompleted){
        return repository.findAllByIsCompleted(isCompleted);
    }

    public TaskModel createTask(TaskModel task){
        return repository.save(task);
    }

    public TaskModel updateTask(TaskModel task){
        return repository.save(task);
    }

    public void deleteTaskById(UUID id){
        repository.deleteById(id);
    }
}
