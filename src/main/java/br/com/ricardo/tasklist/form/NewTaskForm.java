package br.com.ricardo.tasklist.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.ricardo.tasklist.enums.TaskStatus;
import br.com.ricardo.tasklist.model.Task;
import br.com.ricardo.tasklist.model.TaskList;
import br.com.ricardo.tasklist.repository.TaskListRepository;

public class NewTaskForm {

	@NotNull
	@NotEmpty(message = "Description field is required")
	@Length(min = 5, max = 50)
	private String description;
	
	@NotNull
	@NotEmpty(message = "Description field is required")
	@Length(min = 5, max = 50)
	private String taskListName;
	
	public NewTaskForm() {}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaskListName() {
		return taskListName;
	}
	
	public Task convert(TaskListRepository repository) {
		TaskList taskList = repository.findByName(taskListName);
		return new Task(null, description, TaskStatus.TO_DO, taskList);
	}
}
