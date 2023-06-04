package com.kkito.graphqldemo.controllers;

import com.kkito.graphqldemo.dto.CreateTodoDto;
import com.kkito.graphqldemo.dto.UpdateTodoDto;
import com.kkito.graphqldemo.models.Todo;
import com.kkito.graphqldemo.repositories.TodoRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = JpaTestController.BASE_URL)
@RequiredArgsConstructor
public class JpaTestController {

  public static final String BASE_URL = "api/jpaTest";

  private final TodoRepository todoRepository;

  @GetMapping("")
  private ResponseEntity<Iterable<Todo>> getTodos() {
    Iterable<Todo> todos = todoRepository.findAll();
    return ResponseEntity.ok(todos);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Optional<Todo>> getTodo(@PathVariable UUID id) {
    Optional<Todo> todo = todoRepository.findById(id);
    return ResponseEntity.ok(todo);
  }

  @PostMapping("")
  private ResponseEntity<Todo> createTodo(@RequestBody CreateTodoDto dto) {
    Todo newTodo = new Todo();
    newTodo.setTitle(dto.getTitle());
    newTodo.setCompleted(false);
    newTodo.setCreatedAt(LocalDateTime.now());
    newTodo.setDeletedAt(LocalDateTime.now());

    todoRepository.save(newTodo);

    return ResponseEntity.ok(newTodo);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<Todo> deleteTodo(@PathVariable UUID id) {
    Todo todo = _findTodoById(id);
    todoRepository.delete(todo);

    return ResponseEntity.ok(todo);
  }

  @PutMapping("/{id}")
  private ResponseEntity<Todo> updateTodo(
    @RequestBody UpdateTodoDto dto,
    @PathVariable UUID id
  ) {
    Todo todo = _findTodoById(id);

    if (Objects.nonNull(dto.getTitle())) {
      todo.setTitle(dto.getTitle());
    }

    // if (dto)
    todoRepository.save(todo);

    return ResponseEntity.ok(todo);
  }

  private Todo _findTodoById(UUID id) {
    Optional<Todo> todo = todoRepository.findById(id);
    if (todo.isEmpty()) {
      return new Todo();
    }

    return todo.get();
  }
}
