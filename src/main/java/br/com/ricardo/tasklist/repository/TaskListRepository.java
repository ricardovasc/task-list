package br.com.ricardo.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardo.tasklist.model.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {

	TaskList findByName(String name);
}
