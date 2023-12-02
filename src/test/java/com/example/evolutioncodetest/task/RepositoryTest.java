package com.example.evolutioncodetest.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private TaskRepository repository;

    @BeforeEach
    void setUp() {
        var task1 = new TaskModel("task 1", false);
        var task2 = new TaskModel("task 2", true);
        var task3 = new TaskModel("task 3", true);
        var task4 = new TaskModel("description 4", true);

        repository.save(task1);
        repository.save(task2);
        repository.save(task3);
        repository.save(task4);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void testFindByDescriptionReturnsEmptyList(){
        assertThat(repository.findByDescriptionContains("some"))
                .as("Task Repository findByDescriptionContains method should return a " +
                        "empty list of tasks when no task is created")
                .hasSize(0);
    }

    @Test
    void testFindByDescriptionReturnsOneElement(){
        assertThat(repository.findByDescriptionContains("description"))
                .as("Task Repository findByDescription method should return one element " +
                        "that matches the description")
                .hasSize(1)
                .hasOnlyElementsOfType(TaskModel.class)
                .element(0)
                .hasFieldOrPropertyWithValue("description", "description 4");
    }

    @Test
    void testFindByDescriptionReturnsMultipleElements(){
        assertThat(repository.findByDescriptionContains("task"))
                .as("Task Repository findByDescription method should return only elements that matches " +
                        "the description")
                .hasSize(3)
                .hasOnlyElementsOfType(TaskModel.class);
    }

    @Test
    void testFindAllByStatus(){
        assertThat(repository.findAllByIsCompleted(false))
                .as("Task Repository findAllByIsCompleted method should return only the elements that match")
                .hasSize(1)
                .hasOnlyElementsOfType(TaskModel.class)
                .element(0)
                .hasFieldOrPropertyWithValue("isCompleted", false);

        assertThat(repository.findAllByIsCompleted(true))
                .as("Task Repository findAllByIsCompleted method should return only the elements that match")
                .hasSize(3)
                .hasOnlyElementsOfType(TaskModel.class)
                .allMatch(TaskModel::getIsCompleted);
    }
}
