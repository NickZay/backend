package ru.nickzay.todolist.signing;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nickzay.todolist.appuser.AppUser;
import ru.nickzay.todolist.appuser.AppUserService;
import ru.nickzay.todolist.exceptions.GeneralException;

@Service
@AllArgsConstructor
public class SignService {
    private final AppUserService appUserService;

    public String signIn(Request request) throws GeneralException {
        return appUserService.signIn(request);
    }

    public String signUp(Request request) throws GeneralException {
        return appUserService.signUp(
                new AppUser(
                        request.getUsername(),
                        request.getPassword()
                )
        );
    }
}
