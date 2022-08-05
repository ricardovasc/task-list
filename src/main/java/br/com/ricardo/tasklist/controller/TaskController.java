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
import br.com.ricardo.tasklist.model.TaskList;
import br.com.ricardo.tasklist.repository.TaskListRepository;
import br.com.ricardo.tasklist.repository.TaskRepository;

@RestController
@RequestMapping("/task-lists")
public class TaskController {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	TaskListRepository taskListRepository;

	@GetMapping("/{taskListId}/tasks")
	public Page<TaskDto> findAllByTaskListId(@PathVariable Long taskListId,
			@PageableDefault(sort = "description", direction = Direction.ASC, page = 0, size = 12) Pageable pageable) {
		Page<Task> tasks = taskRepository.findByTaskListId(taskListId, pageable);

		return TaskDto.convert(tasks);
	}

	@PostMapping("/{taskListId}/tasks")
	@Transactional
	public ResponseEntity<TaskDto> insert(@PathVariable(value = "taskListId") Long taskListId,
			@RequestBody @Valid NewTaskForm form, UriComponentsBuilder uriBuilder) {
		Optional<TaskList> optional = taskListRepository.findById(taskListId);

		if (optional.isPresent()) {
			TaskList taskList = optional.get();
			Task task = new Task(null, form.getDescription(), TaskStatus.TO_DO, taskList);
			taskRepository.save(task);

			URI uri = uriBuilder.path("/task-lists/{taskListId}").buildAndExpand(task.getId()).toUri();
			return ResponseEntity.created(uri).body(new TaskDto(task));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{taskListId}/tasks/{taskId}")
	@Transactional
	public ResponseEntity<TaskDto> markAsDone(@PathVariable(value = "taskListId") Long taskListId,
			@PathVariable(value = "taskId") Long taskId) {
		Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);

		if (optionalTaskList.isPresent()) {
			TaskList taskList = optionalTaskList.get();

			Optional<Task> optionalTask = taskRepository.findById(taskId);

			if (optionalTask.isPresent()) {
				Task task = optionalTask.get();
				task.setStatus(TaskStatus.DONE);
				taskRepository.save(task);
				return ResponseEntity.ok(new TaskDto(task));
			}

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{taskListId}/tasks/{taskId}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable(value = "taskListId") Long taskListId,
			@PathVariable(value = "taskId") Long taskId) {
		Optional<Task> optional = taskRepository.findByIdAndTaskListId(taskId, taskListId);

		if (optional.isPresent()) {
			taskRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
