package ru.dayagunov.lab_3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dayagunov.lab_3.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public TaskController() {
        tasks.add(new Task(nextId.getAndIncrement(), "Купить продукты"));
        tasks.add(new Task(nextId.getAndIncrement(), "Написать отчет по проекту"));
        tasks.add(new Task(nextId.getAndIncrement(), "Запланировать встречу с командой"));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task newTaskData) {
        // Имитация работы с базой данных
        Task newTask = new Task(nextId.getAndIncrement(), newTaskData.getDescription());
        tasks.add(newTask);

        try {
            Thread.sleep(50); // Создание может быть немного дольше
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
        Task task = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        task.setCompleted(true);
        return ResponseEntity.ok(task);
    }
}