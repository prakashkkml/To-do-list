package com.todo;

import com.todo.model.Task;
import com.todo.model.TaskDto;
import com.todo.repository.TaskRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodotasksApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void init() {
        createTestData();
    }
    private void createTestData() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "shopping", 5));
        tasks.add(new Task(2L, "Kitchen Clean Up", 4));
        tasks.add(new Task(3L, "Meditate", 1));
        tasks.add(new Task(4L, "Coding Practice", 2));
        tasks.add(new Task(5L, "Reading books", 3));
        taskRepository.saveAll(tasks);
    }

    @Test
    public void should_return_not_found_message_when_id_not_exist() {
        ResponseEntity<TaskDto> response = restTemplate.getForEntity("/tasks/10", TaskDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void should_return_not_found_message_when_id_not_exist_while_update() {
        String taskDescriptionEmptyBody = "{ \"id\": 11, \"description\": \"Test description\", \"priority\": 0 }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(taskDescriptionEmptyBody, headers);
        ResponseEntity<TaskDto> response = restTemplate.exchange("/tasks/11", HttpMethod.PUT, entity, TaskDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void should_return_bad_request_status_when_description_is_empty() {
        String taskDescriptionEmptyBody = "{ \"id\": 1, \"description\": \"\", \"priority\": 0 }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(taskDescriptionEmptyBody, headers);
        ResponseEntity<TaskDto> response = restTemplate.exchange("/tasks/1", HttpMethod.PUT, entity, TaskDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void should_update_task() {
        String expectedDescription = "Test Description";
        String taskDescriptionEmptyBody = "{ \"id\": 5, \"description\": \"" + expectedDescription + "\", \"priority\": 0 }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(taskDescriptionEmptyBody, headers);
        ResponseEntity<TaskDto> response = restTemplate.exchange("/tasks/5", HttpMethod.PUT, entity, TaskDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void should_update_task_and_return_task_details_in_body() {
        String expectedDescription = "Test Description";
        String taskDescriptionEmptyBody = "{ \"id\": 1, \"description\": \"" + expectedDescription + "\", \"priority\": 0 }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(taskDescriptionEmptyBody, headers);
        ResponseEntity<TaskDto> response = restTemplate.exchange("/tasks/1", HttpMethod.PUT, entity, TaskDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TaskDto taskUpdatedResponse = response.getBody();
        assertThat(taskUpdatedResponse.getDescription()).isEqualTo(expectedDescription);
    }

    @Test
    public void should_update_task_entity() {
        String expectedDescription = "Test should_update_task_entity Description";
        String taskId = "5";
        String taskDescriptionEmptyBody = "{ \"id\": " + taskId + ", \"description\": \"" + expectedDescription + "\", \"priority\": 0 }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(taskDescriptionEmptyBody, headers);
        ResponseEntity<TaskDto> response = restTemplate.exchange("/tasks/" + taskId, HttpMethod.PUT, entity, TaskDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verifyTaskEntityDescription(taskId, expectedDescription);
    }

    private void verifyTaskEntityDescription(String taskId, String expectedDescription) {
        ResponseEntity<TaskDto> response = restTemplate.getForEntity("/tasks/" + taskId, TaskDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TaskDto taskResponse = response.getBody();
        assertThat(taskResponse.getDescription()).isEqualTo(expectedDescription);
    }
}

