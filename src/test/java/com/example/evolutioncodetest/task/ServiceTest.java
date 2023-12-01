package com.example.evolutioncodetest.task;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Mockito.mock(TaskModel.class);
        Mockito.mock(repository.getClass());

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
        Mockito.mock(TaskModel.class);
        Mockito.mock(repository.getClass());

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
    void testFindByDescription() {

    }

    @Test
    void testFindByStatus() {

    }

    @Test
    void testCreate() {

    }

    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }
}
