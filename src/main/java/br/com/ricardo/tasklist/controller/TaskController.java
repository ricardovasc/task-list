package br.com.ricardo.tasklist.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ricardo.tasklist.dto.TaskDto;
import br.com.ricardo.tasklist.enums.TaskStatus;
import br.com.ricardo.tasklist.form.NewTaskForm;
import br.com.ricardo.tasklist.model.Task;
import br.com.ricardo.tasklist.repository.TaskListRepository;
import br.com.ricardo.tasklist.repository.TaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	TaskListRepository taskListRepository;

	@GetMapping
	public Page<TaskDto> findAll(
			@PageableDefault(sort = "description", direction = Direction.ASC, page = 0, size = 12) Pageable pageable) {
		Page<Task> tasks = taskRepository.findAll(pageable);

		return TaskDto.convert(tasks);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskDto> findById(@PathVariable Long id) {
		Optional<Task> optional = taskRepository.findById(id);

		if (optional.isPresent()) {
			return ResponseEntity.ok(new TaskDto(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<TaskDto> insert(@RequestBody @Valid NewTaskForm form,
			UriComponentsBuilder uriBuilder) {
		Task task = form.convert(taskListRepository);

		taskRepository.save(task);

		URI uri = uriBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
		return ResponseEntity.created(uri).body(new TaskDto(task));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TaskDto> markAsDone(@PathVariable Long id) {
		Optional<Task> optional = taskRepository.findById(id);

		if (optional.isPresent()) {
			Task task = optional.get();
			task.setStatus(TaskStatus.DONE);
			taskRepository.save(task);
			return ResponseEntity.ok(new TaskDto(task));
		}

		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Task> optional = taskRepository.findById(id);

		if (optional.isPresent()) {
			taskRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
