package com.example.evolutioncodetest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskService service;

    @Autowired
    public TaskController(TaskService service){
        this.service = service;
    }

    @GetMapping
    public @ResponseBody List<TaskModel> getAllTasks(){
        return service.getAllTasks();
    }

    @GetMapping
    public @ResponseBody List<TaskModel> getAllTasksWithDescription(@RequestParam(name = "desc", required = true) String description){
        return service.getTaskByDescription(description);
    }

    @GetMapping
    public @ResponseBody List<TaskModel> getAllTasksWithStatus(@RequestParam boolean status){
        return service.getTaskByStatus(status);
    }

    @PostMapping
    public @ResponseBody TaskModel createTask(@RequestBody TaskModel task){
        return service.createTask(task);
    }

    @PutMapping("/{id}")
    public @ResponseBody TaskModel updateTask(@RequestBody TaskDTO task, @PathVariable UUID id){
        return service.updateTask(task, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id){
        service.deleteTaskById(id);
    }
}
