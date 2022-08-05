package br.com.ricardo.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardo.tasklist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
