package com.todo.controller;

import com.todo.model.Task;
import com.todo.model.TaskDto;
import com.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    @Operation(summary = "To fetch all the available tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = taskService.findAll();
        List<TaskDto> taskDtoList = tasks.stream().map(this::getTaskDtoFunction).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(taskDtoList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "To fetch the task with task id")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find task with given id");
        TaskDto taskDto = getTaskDtoFunction(optionalTask.get());
        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "To update the task with task id")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find task with given id");
        if (Objects.isNull(taskDto.getDescription()) || taskDto.getDescription().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task description is required");
        Task updatedTask = taskService.updateTask(optionalTask.get(), taskDto);
        return ResponseEntity.status(HttpStatus.OK).body(getTaskDtoFunction(updatedTask));
    }

    @PostMapping
    @Operation(summary = "To create the task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.OK).body(getTaskDtoFunction(task));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "To delete the task with task id")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find task with given id");
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body("Task removed successfully!");
    }

    private TaskDto getTaskDtoFunction(Task task) {
        return new TaskDto(task.getId(), task.getDescription(), task.getPriority());
    }
}
