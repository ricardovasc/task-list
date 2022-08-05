package br.com.ricardo.tasklist.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class NewTaskForm {

	@NotNull
	@NotEmpty(message = "Description field is required")
	@Length(min = 5, max = 50)
	private String description;
	
	public NewTaskForm() {}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
