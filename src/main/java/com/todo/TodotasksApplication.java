package com.todo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Todo Task Application", version = "1.0.0"))
public class TodotasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodotasksApplication.class, args);
	}

}
