package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }
    public TaskDto getTask(Long taskId) {
        return  new TaskDto(1L, "test title", "test_content");
    }
    public void deleteTask(Long taskId) {

    }
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }
    public void createTask() {

    }
}
