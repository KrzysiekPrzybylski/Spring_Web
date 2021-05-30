package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNoFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbServiceTestSuite {

    @Autowired
    private DbService dbService;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveTask(){
        //Given
        Task task = new Task( null,"testName", "testDescription");
        //When
        dbService.saveTask(task);
        Long idTask = task.getId();
        //Then
        assertNotEquals(0l, idTask );
        assertEquals("testName", task.getTitle());
        assertNotNull(idTask);
        //CleanUp
        taskRepository.deleteById(idTask);
    }
    @Test
    public void testRemoveTask(){
        //Given
        Task task = new Task(null,"testName", "testDescription");
        //When
        dbService.saveTask(task);
        Long taskId = task.getId();
        dbService.deleteTask(task.getId());
        //Then
        assertFalse(taskRepository.existsById(taskId));
    }
    @Test
    void shouldFetchAllTasks() {
        //Given
        Task task = new Task(null, "Test_title", "testContent");
        Task task1 = new Task(null, "Test_title", "testContent");
        Task task2 = new Task(null, "Test_title", "testContent");
        dbService.saveTask(task);
        dbService.saveTask(task1);
        dbService.saveTask(task2);
        long id = task.getId();
        //When
        List<Task> tasks = dbService.getAllTasks();
        //Then
        assertEquals(tasks.size(), taskRepository.findAll().size());
        assertNotEquals(0, id);
        assertTrue(taskRepository.existsById(task2.getId()));
        assertTrue(taskRepository.findById(task1.getId()).isPresent());
        //CleanUp
        taskRepository.deleteById(id);
    }
    @Test
    void shouldDownloadOneTask() throws TaskNoFoundException {
        //Given
        Task task = new Task(null, "Test_title", "testContent");
        dbService.saveTask(task);
        long id = task.getId();
        //When
        Optional<Task> downloadedTask = dbService.getTask(id);
        //Then
        assertEquals("Test_title", downloadedTask.get().getTitle());
        assertEquals("testContent", downloadedTask.get().getContent());
    }

}