package com.dvlprmatheus.ticklate;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dvlprmatheus.ticklate.domain.Task;
import com.dvlprmatheus.ticklate.domain.TaskRepository;

@EnableScheduling
@SpringBootApplication
public class TicklateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicklateApplication.class, args);
	}

	@Bean
	CommandLineRunner seed(TaskRepository taskRepository) {
		return args -> {
			List<Task> tasks = List.of(
				new Task("Estudando SOLID", LocalDateTime.now().plusSeconds(10)),
				new Task("MVP Ticklate", LocalDateTime.now().plusSeconds(20))
			);
			taskRepository.saveAll(tasks);
		};
	}
}
