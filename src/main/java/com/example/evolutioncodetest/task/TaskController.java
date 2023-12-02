package com.example.evolutioncodetest.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public @ResponseBody List<TaskModel> getTasks(@RequestParam Optional<String> description,
                                                  @RequestParam Optional<Boolean> completed){
        if (description.isPresent()) {
            return service.getTaskByDescription(description.get());
        }

        if (completed.isPresent()) {
            return service.getTaskByStatus(completed.get());
        }

        return service.getAllTasks();
    }

    @GetMapping("{id}")
    public @ResponseBody TaskModel getTaskById(@PathVariable UUID id){
        return service.getTaskById(id);
    }

    @PostMapping
    public @ResponseBody TaskModel createTask(@RequestBody TaskDTO task){
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
