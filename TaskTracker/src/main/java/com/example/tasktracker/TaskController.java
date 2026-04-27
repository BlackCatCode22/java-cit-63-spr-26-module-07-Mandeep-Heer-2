package com.example.tasktracker;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private List<Task> database = new ArrayList<>();

    @GetMapping
    public List<Task> getAllTasks() {
        return database;
    }

    @PostMapping
    public Task addTask(@RequestBody Task newTask) {
        Task taskToSave = new Task(newTask.getDescription());
        database.add(taskToSave);
        return taskToSave;
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable String id) {
        database.removeIf(task -> task.getId().equals(id));
        return "Task " + id + " deleted successfully!";
    }
}