package ru.nickzay.todolist.todo;

import ru.nickzay.todolist.appuser.AppUser;
import ru.nickzay.todolist.exceptions.GeneralException;
import ru.nickzay.todolist.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TokenService tokenService;

    public void addTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> findTodoListByToken(String token) throws GeneralException {
        AppUser appUser = tokenService.findAppUserByToken(token);
        return todoRepository.findByAppUserOrderByCreatedAt(appUser);
    }

    public List<Todo> findTodoListByAppUser(AppUser appUser) {
        return todoRepository.findByAppUserOrderByCreatedAt(appUser);
    }

    public Todo findTodoById(Long todo_id) throws GeneralException {
        return todoRepository.findById(todo_id).orElseThrow(() ->
                new GeneralException("todo not found"));
    }
}
