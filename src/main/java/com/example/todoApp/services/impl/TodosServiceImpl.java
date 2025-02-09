package com.example.todoApp.services.impl;

import com.example.todoApp.domains.Todo;
import com.example.todoApp.repository.TodosRepository;
import com.example.todoApp.services.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodosServiceImpl implements TodosService {

    private final TodosRepository todosRepository;

    @Override
    public List<Todo> findAll() {
        return todosRepository.findAll();
    }

    @Override
    public Todo findById(Long id) {
        return todosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
    }

    @Override
    public Todo saveOrUpdate(Todo todo) {
        if (todo.getId() != null) {
            Todo existingTodo = todosRepository.findById(todo.getId()).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
            existingTodo.setTitle(todo.getTitle());
            existingTodo.setDone(todo.isDone());
            return todosRepository.save(existingTodo);
        }
        return todosRepository.save(todo);
    }

    @Override
    public List<Todo> search(String queryParam) {
        return todosRepository.findByTitleContainingIgnoreCase(queryParam);
    }

    @Override
    public void delete(Long id) {
        todosRepository.deleteById(id);
    }
}
