package com.example.todoApp.controllers;

import com.example.todoApp.domains.Todo;
import com.example.todoApp.services.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class TodosController {

    private final TodosService todosService;

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getTodos() {
        return ResponseEntity.ok(todosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todosService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Todo>> search(@RequestParam String queryParam) {
        return ResponseEntity.ok(todosService.search(queryParam));
    }

    @PostMapping("/")
    public ResponseEntity<Todo> saveOrUpdate(@RequestBody Todo todo) {
        return ResponseEntity.ok(todosService.saveOrUpdate(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todosService.delete(id);
        return ResponseEntity.ok().build();
    }
}
