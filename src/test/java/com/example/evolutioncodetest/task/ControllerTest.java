package com.example.evolutioncodetest.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    @Test
    void testGetAllTasks() throws Exception {
        when(service.getAllTasks()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/tasks"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$")
                        .isArray())
                .andExpect(jsonPath("$")
                        .isEmpty());

        var task1 = new TaskModel("desc1", false);
        var task2 = new TaskModel("desc2", true);

        var taskList = new ArrayList<TaskModel>();
        taskList.add(task1);
        taskList.add(task2);

        when(service.getAllTasks()).thenReturn(taskList);
        mockMvc.perform(get("/tasks"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(jsonPath("$")
                        .isArray())
                .andExpectAll(
                        jsonPath("$[0].description").isString(),
                        jsonPath("$[0].completed").isBoolean(),
                        jsonPath("$[1].description").isString(),
                        jsonPath("$[1].completed").isBoolean()
                );
    }

}
