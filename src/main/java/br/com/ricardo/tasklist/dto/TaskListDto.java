package br.com.ricardo.tasklist.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.ricardo.tasklist.model.TaskList;

public class TaskListDto {

	private Long id;
	private String name;
	List<TaskDto> tasks = new ArrayList<>();

	public TaskListDto(TaskList taskList) {
		super();
		this.id = taskList.getId();
		this.name = taskList.getName();
		this.tasks = taskList.getTasks().stream().map(TaskDto::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<TaskDto> getTasks() {
		return tasks;
	}
	
	public static Page<TaskListDto> convert(Page<TaskList> taskLists) {
		return taskLists.map(TaskListDto::new);
	}
}
