package ru.nickzay.todolist.registration;

import ru.nickzay.todolist.appuser.AppUser;
import ru.nickzay.todolist.appuser.AppUserRole;
import ru.nickzay.todolist.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;

    public String register(RegistrationRequest request) {
        return appUserService.signUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
