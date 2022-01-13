package ru.nickzay.todolist.todo;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nickzay.todolist.token.TokenService;

import java.util.List;

@RestController
@RequestMapping(path = "/todo")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final TokenService tokenService;

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getTodoListByToken(@RequestParam("token") String token) {
        return new ResponseEntity<>(todoService.findTodoListByToken(token), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTodo(@RequestParam("token") String token, @RequestBody Todo todo) {
        String newToken = tokenService.checkAndUpdateToken(token, todo.getAppUser());
        todoService.addTodo(todo);
        return new ResponseEntity<>(newToken, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTodo(@RequestParam("token") String token, @RequestBody Todo todo) {
        String newToken = tokenService.checkAndUpdateToken(token, todo.getAppUser());
        todoService.updateTodo(todo);
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTodo(@RequestParam("token") String token, @RequestBody Todo todo) {
        String newToken = tokenService.checkAndUpdateToken(token, todo.getAppUser());
        todoService.deleteTodo(todo.getId());
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }
}
