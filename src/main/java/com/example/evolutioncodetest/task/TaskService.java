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
            throw new NoSuchElementException(String.format("The Task with the id %s doesn't exists", id));
        }
        return task.get();
    }

    public List<TaskModel> getTaskByDescription(String description){
        return repository.findByDescriptionContains(description);
    }

    public List<TaskModel> getTaskByCompletedStatus(boolean completed){
        return repository.findAllByIsCompleted(completed);
    }

    public TaskModel createTask(TaskDTO task){
        if(task.getDescription() == null || task.getDescription().isEmpty()){
            throw new RestClientResponseException("Task description can't be empty", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }

        return repository.save(new TaskModel(task.getDescription(), task.getIsCompleted()));
    }

    public TaskModel updateTask(TaskDTO updateBody, UUID taskId){
        var taskToUpdate = repository.findById(taskId);

        if(taskToUpdate.isEmpty()){
            throw new NoSuchElementException(String.format("Task with id %s doesn't exists", taskId));
        }

        var newTask = taskToUpdate.get();

        if (updateBody.getDescription() != null && !updateBody.getDescription().isEmpty()) {
            newTask.setDescription(updateBody.getDescription());
        }

        if(updateBody.getIsCompleted() != null){
            newTask.setCompleted(updateBody.getIsCompleted());
        }

        return repository.save(newTask);
    }

    public void deleteTaskById(UUID id){
        if(repository.findById(id).isEmpty()){
            throw new NoSuchElementException(String.format("Task with id %s doesn't exists", id));
        }
        repository.deleteById(id);
    }
}
