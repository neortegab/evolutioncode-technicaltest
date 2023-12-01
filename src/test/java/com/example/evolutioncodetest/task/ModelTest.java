package com.example.evolutioncodetest.task;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class ModelTest {
    @Test
    void testModelDefinition(){
        assertThat(TaskModel.class)
                .as("Task model should have the property \"id\"")
                .hasDeclaredFields("id");
        assertThat(TaskModel.class)
                .as("Task model should have the property \"description\"")
                .hasDeclaredFields("description");
        assertThat(TaskModel.class)
                .as("Task model should have the property \"status\"")
                .hasDeclaredFields("status");
    }

    @Test
    void testModelTypes() throws NoSuchFieldException {

        assertThat(TaskModel.class.getDeclaredField("id").getType().getTypeName())
                .as("Task model id should be of type UUID")
                        .isEqualTo("java.util.UUID");

        assertThat(TaskModel.class.getDeclaredField("description").getType().getTypeName())
                .as("Task model description should be of type String")
                .isEqualTo("java.lang.String");

        assertThat(TaskModel.class.getDeclaredField("isCompleted").getType().getTypeName())
                .as("Task model status should be of type boolean")
                .isEqualTo("boolean");
    }
}
