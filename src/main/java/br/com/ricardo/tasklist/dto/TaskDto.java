package br.com.ricardo.tasklist.dto;

import org.springframework.data.domain.Page;

import br.com.ricardo.tasklist.enums.TaskStatus;
import br.com.ricardo.tasklist.model.Task;

public class TaskDto {

	private Long id;
	private String description;
	private TaskStatus status;
	
	public TaskDto(Task task) {
		super();
		this.id = task.getId();
		this.description = task.getDescription();
		this.status = task.getStatus();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public static Page<TaskDto> convert(Page<Task> tasks) {
		return tasks.map(TaskDto::new);
	}
}
