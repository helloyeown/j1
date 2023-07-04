package org.zerock.j1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j1.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {	// pk타입(Long)
	
}
