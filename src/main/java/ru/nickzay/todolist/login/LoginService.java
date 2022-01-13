package ru.nickzay.todolist.login;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nickzay.todolist.appuser.AppUser;
import ru.nickzay.todolist.appuser.AppUserService;

@Service
@AllArgsConstructor
public class LoginService {
    private final AppUserService appUserService;

    public String signIn(LoginRequest request) {
        return appUserService.signIn(request);
    }
}
