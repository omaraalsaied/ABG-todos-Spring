package com.example.todoApp.repository;

import com.example.todoApp.domains.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodosRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTitleContainingIgnoreCase(String queryParam);

}
