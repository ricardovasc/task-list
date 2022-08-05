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

import br.com.ricardo.tasklist.dto.TaskListDto;
import br.com.ricardo.tasklist.form.NewTaskListForm;
import br.com.ricardo.tasklist.form.UpdateTaskListForm;
import br.com.ricardo.tasklist.model.TaskList;
import br.com.ricardo.tasklist.repository.TaskListRepository;
import br.com.ricardo.tasklist.repository.TaskRepository;

@RestController
@RequestMapping("/task-lists")
public class TaskListController {

	@Autowired
	TaskListRepository taskListRepository;

	@Autowired
	TaskRepository taskRepository;

	@GetMapping
	public Page<TaskListDto> findAll(
			@PageableDefault(sort = "name", direction = Direction.ASC, page = 0, size = 12) Pageable pageable) {
		Page<TaskList> taskLists = taskListRepository.findAll(pageable);

		return TaskListDto.convert(taskLists);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskListDto> findById(@PathVariable Long id) {
		Optional<TaskList> optional = taskListRepository.findById(id);

		if (optional.isPresent()) {
			return ResponseEntity.ok(new TaskListDto(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<TaskListDto> insert(@RequestBody @Valid NewTaskListForm form,
			UriComponentsBuilder uriBuilder) {
		TaskList taskList = form.convert();

		taskListRepository.save(taskList);

		URI uri = uriBuilder.path("/task-lists/{id}").buildAndExpand(taskList.getId()).toUri();
		return ResponseEntity.created(uri).body(new TaskListDto(taskList));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TaskListDto> update(@PathVariable Long id, @RequestBody @Valid UpdateTaskListForm form) {
		Optional<TaskList> optional = taskListRepository.findById(id);

		if (optional.isPresent()) {
			TaskList taskList = optional.get();
			taskList.setName(form.getName());
			taskListRepository.save(taskList);
			return ResponseEntity.ok(new TaskListDto(taskList));
		}

		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<TaskList> optional = taskListRepository.findById(id);

		if (optional.isPresent()) {
			taskListRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
