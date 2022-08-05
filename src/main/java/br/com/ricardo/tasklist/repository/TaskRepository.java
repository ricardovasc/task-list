package br.com.ricardo.tasklist.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardo.tasklist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	Page<Task> findByTaskListId(Long taskListId, Pageable page);
	
	Optional<Task> findByIdAndTaskListId(Long id, Long taskListId);
}
