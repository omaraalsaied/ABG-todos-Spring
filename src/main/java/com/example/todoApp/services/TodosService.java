package com.example.todoApp.services;

import com.example.todoApp.domains.Todo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodosService {

    List<Todo> findAll();

    Todo findById(Long id);

    Todo saveOrUpdate(Todo todo);

    List<Todo> search(String queryParam);

    void delete(Long id);
}

