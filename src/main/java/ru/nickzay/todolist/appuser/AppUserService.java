package ru.nickzay.todolist.appuser;

import ru.nickzay.todolist.login.LoginRequest;
import ru.nickzay.todolist.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    public String signUp(AppUser appUser) {
        if (appUserRepository.findByUsername(appUser.getUsername()).isPresent()) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        return tokenService.createToken(appUser);
    }

    public String signIn(LoginRequest loginRequest) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(loginRequest.getUsername());
        if (appUserOptional.isEmpty()) {
            throw new IllegalStateException("user does not exist");
        } else {
            AppUser appUser = appUserOptional.get();
            String encodedPassword = bCryptPasswordEncoder.encode(loginRequest.getPassword());
            if (!encodedPassword.equals(appUser.getPassword())) {
                throw new IllegalStateException("password does not match");
            } else {
                return tokenService.createToken(appUser);
            }
        }
    }
}
