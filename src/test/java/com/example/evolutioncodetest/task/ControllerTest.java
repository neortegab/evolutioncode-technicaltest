package com.example.evolutioncodetest.task;

import com.example.evolutioncodetest.ControllerAdvisor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService service;


    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TaskController(service))
                .setControllerAdvice(ControllerAdvisor.class)
                .build();

    }

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
                        jsonPath("$[0].description").value("desc1"),
                        jsonPath("$[0].isCompleted").value(false),
                        jsonPath("$[1].description").value("desc2"),
                        jsonPath("$[1].isCompleted").value(true)
                );
    }

    @Test
    void testGetById() throws Exception{
        var task = new TaskModel();
        task.setId(UUID.randomUUID());
        task.setDescription("description1");
        task.setIsCompleted(true);

        when(service.getTaskById(task.getId())).thenReturn(task);
        mockMvc.perform(
                get("/tasks/{taskId}", task.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                   status().isOk()
                );
    }

    @Test
    void testCreateTask() throws Exception{
        var taskDto = new TaskDTO();
        taskDto.setDescription("test description");

        var taskModel = new TaskModel();
        taskModel.setDescription(taskDto.getDescription());
        taskModel.setIsCompleted(false);
        taskModel.setId(UUID.randomUUID());

        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        var writer = mapper.writer().withDefaultPrettyPrinter();
        var requestJson = writer.writeValueAsString(taskDto);

        when(service.createTask(taskDto)).thenReturn(taskModel);
        mockMvc.perform(
                    post("/tasks")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.id").isString(),
                        jsonPath("$.description").value("test description"),
                        jsonPath("$.isCompleted").value(false)
                );
    }

    @Test
    void testUpdateTask() throws Exception{
        var taskId = UUID.randomUUID();

        var taskDto = new TaskDTO();
        taskDto.setDescription("test description");
        taskDto.setIsCompleted(true);

        var taskModel = new TaskModel();
        taskModel.setDescription("old description");
        taskModel.setIsCompleted(false);
        taskModel.setId(taskId);

        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        var writer = mapper.writer().withDefaultPrettyPrinter();
        var requestJson = writer.writeValueAsString(taskDto);

        when(service.createTask(taskDto)).thenReturn(taskModel);
        mockMvc.perform(
                        put("/tasks/{id}", taskId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }
}
