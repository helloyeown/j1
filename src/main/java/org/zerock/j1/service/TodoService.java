package org.zerock.j1.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;

@Transactional
public interface TodoService {
	
	PageResponseDTO<TodoDTO> getList();

	TodoDTO register(TodoDTO dto);

	TodoDTO getOne(Long tno);

	void remove(Long tno);

	void modify(TodoDTO dto);

}
