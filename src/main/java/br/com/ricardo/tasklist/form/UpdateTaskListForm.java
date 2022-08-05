package br.com.ricardo.tasklist.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.ricardo.tasklist.model.TaskList;

public class UpdateTaskListForm {

	@NotNull
	@NotEmpty(message = "Name field is required")
	@Length(min = 5, max = 50)
	private String name;
	
	public UpdateTaskListForm() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public TaskList convert() {
		return new TaskList(null, name);
	}
}
