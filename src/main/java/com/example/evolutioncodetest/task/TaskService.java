package com.example.evolutioncodetest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public List<TaskModel> getAllTasks(){
        return repository.findAll();
    }

    public TaskModel getTaskById(UUID id){
        var task = repository.findById(id);
        if(task.isEmpty()){
            throw new NoSuchElementException(String.format("The Task with the id %b doesn't exists", id));
        }
        return task.get();
    }

    public List<TaskModel> getTaskByDescription(String description){
        return repository.findByDescriptionContains(description);
    }

    public List<TaskModel> getTaskByStatus(boolean isCompleted){
        return repository.findAllByIsCompleted(isCompleted);
    }

    public TaskModel createTask(TaskModel task){
        if(repository.findById(task.getId()).isPresent()){
            throw new RestClientResponseException(
                        String.format("Task with ID %b already exists", task.getId()),
                        HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        null,
                        null,
                        null
                    );
        }
        return repository.save(task);
    }

    public TaskModel updateTask(TaskDTO task, UUID taskId){
        var taskToUpdate = repository.findById(taskId);

        if(taskToUpdate.isEmpty()){
            throw new NoSuchElementException(String.format("Task with id %b doesn't exists", taskId));
        }

        var newTask = taskToUpdate.get();

        if (task.getDescription() != null && !task.getDescription().isEmpty()) {
            newTask.setDescription(task.getDescription());
        }

        if(task.getIsCompleted() != null){
            newTask.setCompleted(task.getIsCompleted());
        }

        return repository.save(newTask);
    }

    public void deleteTaskById(UUID id){
        if(repository.findById(id).isEmpty()){
            throw new NoSuchElementException(String.format("Task with id %b doesn't exists", id));
        }
        repository.deleteById(id);
    }
}
