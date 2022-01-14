package ru.nickzay.todolist.todo;

import ru.nickzay.todolist.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByAppUserOrderByCreatedAt(AppUser appUser);
}
