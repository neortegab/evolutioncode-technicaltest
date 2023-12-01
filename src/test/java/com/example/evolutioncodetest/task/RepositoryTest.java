package com.example.evolutioncodetest.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private TaskRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testFindByDescriptionReturnsEmptyList(){
        assertThat(repository.findByDescription("some description"))
                .as("Task Repository findByDescription method should return a empty list of tasks when no " +
                        "task is created")
                .hasSize(0);
    }

    @Test
    void testFindByDescriptionReturnsOneElement(){
        var task1 = new TaskModel("task 1");
        repository.save(task1);

        assertThat(repository.findByDescription("task"))
                .as("Task Repository findByDescription method should return one element that matches the description")
                .hasSize(1)
                .hasOnlyElementsOfType(TaskModel.class)
                .element(0)
                .hasFieldOrPropertyWithValue("description", "task 1");
    }

    @Test
    void testFindByDescriptionReturnsMultipleElements(){
        var task1 = new TaskModel("task 1");
        repository.save(task1);

        var task2 = new TaskModel("task 2");
        repository.save(task2);

        var task3 = new TaskModel("task 3");
        repository.save(task3);

        var task4 = new TaskModel("description 4");
        repository.save(task4);

        assertThat(repository.findByDescription("task"))
                .as("Task Repository findByDescription method should return only elements that matches the description")
                .hasSize(3)
                .hasOnlyElementsOfType(TaskModel.class);
    }
}
