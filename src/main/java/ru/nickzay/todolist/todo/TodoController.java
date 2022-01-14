package ru.nickzay.todolist.todo;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nickzay.todolist.appuser.AppUser;
import ru.nickzay.todolist.exceptions.GeneralException;
import ru.nickzay.todolist.token.TokenService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/todo")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final TokenService tokenService;

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getTodoListByToken(@RequestParam("token") String token) throws GeneralException {
        return new ResponseEntity<>(todoService.findTodoListByToken(token), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTodo(@RequestParam("token") String token, @RequestBody String task) {
        try {
            AppUser appUser = tokenService.findAppUserByToken(token);
            String newToken = tokenService.checkAndUpdateToken(token, appUser);
            todoService.addTodo(new Todo(task,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    appUser));
            return new ResponseEntity<>(newToken, HttpStatus.CREATED);
        } catch (GeneralException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateTodo(@RequestParam("token") String token,
                                             @RequestParam("todo_id") Long todo_id,
                                             @RequestParam("task") String task,
                                             @RequestParam("completed") Boolean completed) {
        try {
            System.out.println("TOKEN: " + token);
            System.out.println("TODO: " + todo_id);
            Todo todo = todoService.findTodoById(todo_id);
            String newToken = tokenService.checkAndUpdateToken(token, todo.getAppUser());
            todo.setTask(task);
            todo.setCompleted(completed);
            todo.setUpdatedAt(LocalDateTime.now());
            todoService.updateTodo(todo);
            return new ResponseEntity<>(newToken, HttpStatus.OK);
        } catch (GeneralException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTodo(@RequestParam("token") String token,
                                             @RequestParam("todo_id") Long todo_id) {
        try {
            Todo todo = todoService.findTodoById(todo_id);
            String newToken = tokenService.checkAndUpdateToken(token, todo.getAppUser());
            todoService.deleteTodo(todo.getId());
            return new ResponseEntity<>(newToken, HttpStatus.OK);
        } catch (GeneralException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
