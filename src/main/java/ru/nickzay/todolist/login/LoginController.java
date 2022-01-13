package ru.nickzay.todolist.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/login")
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public String register(@RequestBody LoginRequest request) {
        return loginService.signIn(request);
    }
}
