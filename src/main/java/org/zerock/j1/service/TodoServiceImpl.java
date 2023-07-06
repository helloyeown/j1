package org.zerock.j1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;
import org.zerock.j1.repositories.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
	
	private final TodoRepository todoRepository;
	private final ModelMapper modelMapper;

	@Override
	public PageResponseDTO<TodoDTO> getList() {
		
		Pageable pageable = PageRequest.of(0, 20, Sort.by("tno").descending());

		Page<Todo> result = todoRepository.findAll(pageable);

		List<TodoDTO> dtoList = 
		result.getContent().stream().map(todo -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());
		
		// PageResponseDTO<TodoDTO> response = new PageResponseDTO<>();
		// response.setDtoList(dtoList);
		// return response;

		return null;

	}

	@Override
	public TodoDTO register(TodoDTO dto) {
		
		Todo entity = modelMapper.map(dto, Todo.class);

		Todo result = todoRepository.save(entity);	// 번호가 따져있는 todo

		return modelMapper.map(result, TodoDTO.class);		// result -> TodoDTO

	}

	@Override
	public TodoDTO getOne(Long tno) {

		Optional<Todo> result = todoRepository.findById(tno);

		Todo todo = result.orElseThrow();

		TodoDTO dto = modelMapper.map(todo, TodoDTO.class);

		return dto;

	}

	@Override
	public void remove(Long tno) {
		todoRepository.deleteById(tno);
	}

	@Override
	public void modify(TodoDTO dto) {

		Optional<Todo> result = todoRepository.findById(dto.getTno());

		Todo todo = result.orElseThrow();

		todo.changeTitle(dto.getTitle());

		todoRepository.save(todo);

	}

}
