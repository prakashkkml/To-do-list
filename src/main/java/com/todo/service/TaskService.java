package com.todo.service;

import com.todo.model.Task;
import com.todo.model.TaskDto;
import com.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(TaskDto taskDto) {
        return taskRepository.save(getTaskEntity(taskDto));
    }

    public Task updateTask(Task task, TaskDto taskDto) {
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        return taskRepository.save(task);
    }

    private Task getTaskEntity(TaskDto taskDto) {
        return Task.builder().description(taskDto.getDescription()).priority(taskDto.getPriority()).build();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
