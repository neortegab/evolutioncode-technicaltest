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
    public @ResponseBody List<TaskModel> getTasks(@RequestParam(name = "desc", required = false) String description,
                                                  @RequestParam(required = false) Boolean status){
        if (description != null && !description.isEmpty()) {
            return service.getTaskByDescription(description);
        }

        if (status != null) {
            return service.getTaskByStatus(status);
        }

        return service.getAllTasks();
    }

    @GetMapping("{id}")
    public @ResponseBody TaskModel getTaskById(@PathVariable UUID id){
        return service.getTaskById(id);
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
