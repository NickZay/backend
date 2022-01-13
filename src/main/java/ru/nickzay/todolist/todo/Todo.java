package ru.nickzay.todolist.todo;

import ru.nickzay.todolist.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Todo {
    @SequenceGenerator(
            name = "todo_list_sequence",
            sequenceName = "todo_list_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todo_list_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String task;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public Todo(String task,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                AppUser appUser) {
        this.task = task;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.appUser = appUser;
    }
}