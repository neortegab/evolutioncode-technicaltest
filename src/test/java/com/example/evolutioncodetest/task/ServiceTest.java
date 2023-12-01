package com.example.evolutioncodetest.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientResponseException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

public class ServiceTest {

    @Mock
    private TaskRepository repository;
    private TaskService service;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        service = new TaskService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testFindAll() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<TaskModel>());
        assertThat(service.getAllTasks())
                .as("Get all tasks should return an empty list when there is no tasks added")
                .isEmpty();

        var singleTaskList = new ArrayList<TaskModel>();
        singleTaskList.add(new TaskModel("Mock task"));
        Mockito.when(repository.findAll()).thenReturn(singleTaskList);
        assertThat(service.getAllTasks())
                .as("Get all tasks should return a list with one element when there is one task added")
                .hasSize(1)
                .hasOnlyElementsOfType(TaskModel.class);

        var multipleTaskList = new ArrayList<TaskModel>();
        multipleTaskList.add(new TaskModel("Mock task 1"));
        multipleTaskList.add(new TaskModel("Mock task 2"));
        multipleTaskList.add(new TaskModel("Mock task 3"));
        Mockito.when(repository.findAll()).thenReturn(multipleTaskList);
        assertThat(service.getAllTasks())
                .as("Get all tasks should return a list with all existing tasks")
                .hasSize(3)
                .hasOnlyElementsOfType(TaskModel.class);
    }

    @Test
    void testFindById() {
        var id = UUID.randomUUID();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getTaskById(id))
                .as("Service getById should throw an exception when the id doesn't exists")
                .isInstanceOf(NoSuchElementException.class);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(new TaskModel(id, "mock task", false)));
        assertThat(service.getTaskById(id))
                .as("Service getById should return a not null Task Model with the specified id")
                .isNotNull()
                .isInstanceOf(TaskModel.class)
                .hasFieldOrPropertyWithValue("id", id);
    }

    @Test
    void testCreate() {
        var randomId = UUID.randomUUID();
        Mockito.when(repository.findById(randomId)).thenReturn(Optional.of(new TaskModel()));
        assertThatThrownBy(() -> service.createTask(new TaskModel(randomId, "desc", false)))
                .as("Create task should throw a RestClientResponseException if the ID already exists")
                .isInstanceOf(RestClientResponseException.class);

        Mockito.when(repository.findById(randomId)).thenReturn(Optional.empty());
        var createdTask = new TaskModel();
        Mockito.when(repository.save(createdTask)).thenReturn(createdTask);
        assertThat(service.createTask(createdTask))
                .as("Create task should create the task when task id not found")
                .isNotNull().isInstanceOf(TaskModel.class).isEqualTo(createdTask);
    }

    @Test
    void testUpdate() {
        var randomId = UUID.randomUUID();

        Mockito.when(repository.findById(randomId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.updateTask(new TaskModel(randomId, "desc", false)))
                .as("Update task should throw a NoSuchElementException if the ID doesn't exists")
                .isInstanceOf(NoSuchElementException.class);

        var updatedTask = new TaskModel(randomId, "desc", true);

        Mockito.when(repository.findById(randomId)).thenReturn(Optional.of(new TaskModel(randomId, "desc", false)));
        Mockito.when(repository.save(updatedTask)).thenReturn(updatedTask);
        assertThat(service.updateTask(updatedTask))
                .as("Update task should update the task when the task id is found")
                .isNotNull().isInstanceOf(TaskModel.class).isEqualTo(updatedTask);
    }

    @Test
    void testDelete() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.deleteTaskById(UUID.randomUUID()))
                .as("Delete method should throw a NoSuchElementException when the Task doesn't exists.")
                .isInstanceOf(NoSuchElementException.class);
    }
}
