package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;


@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private DbService dbService;

    @Test
    public void getTaskTest() throws Exception {
        //Given
        Task task = new Task(1L, "Task 1", "description 1");
        TaskDto taskDto = new TaskDto(1L, "Task 1", "description 1");
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.getTask(1L)).thenReturn(Optional.of(task));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTask?taskId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("description 1")));
    }
    @Test
    public void getTasksTest() throws Exception {

        //Given
        Task task1 = new Task(1L, "Task 1", "description 1");
        Task task2 = new Task(2L, "Task 2", "description 2");
        TaskDto task1Dto = new TaskDto(1L, "Task 1", "description 1");
        TaskDto task2Dto = new TaskDto(2L, "Task 2", "description 2");
        List<Task> tasks = Arrays.asList(task1, task2);
        List<TaskDto> taskDtos = Arrays.asList(task1Dto, task2Dto);

        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);
        when(dbService.getAllTasks()).thenReturn(tasks);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("description 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Task 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("description 2")));
    }
    @Test
    public void updateTaskTest() throws Exception {

        //Given
        Task task = new Task(1L, "Task 1", "description 1");
        TaskDto taskDto = new TaskDto(1L, "Task 1", "description 1");
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("description 1")));
    }
    @Test
    public void deleteTaskTest() throws Exception {
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask?taskId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void createTaskTest() throws Exception {

        //Given
        Task task = new Task(1L, "Task 1", "description 1");
        TaskDto taskDto = new TaskDto(1L, "Task 1", "description 1");
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
            .perform(MockMvcRequestBuilders
                .post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsContent))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}