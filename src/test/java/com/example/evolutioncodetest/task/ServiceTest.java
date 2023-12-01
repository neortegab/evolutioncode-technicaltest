package com.example.evolutioncodetest.task;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(service.getAllTasks())
    }

    @Test
    void testFindById() {

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
