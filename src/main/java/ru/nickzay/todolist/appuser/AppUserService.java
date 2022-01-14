package ru.nickzay.todolist.appuser;

import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.nickzay.todolist.exceptions.GeneralException;
import ru.nickzay.todolist.signing.Request;
import ru.nickzay.todolist.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new GeneralException("user not found"));
    }

    public String signUp(AppUser appUser) throws GeneralException {
        if (appUserRepository.findByUsername(appUser.getUsername()).isPresent()) {
            throw new GeneralException("username already taken");
        }
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(appUser);

        return tokenService.createToken(appUser);
    }

    public String signIn(Request loginRequest) throws GeneralException {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(loginRequest.getUsername());
        if (appUserOptional.isEmpty()) {
            throw new GeneralException("user does not exist");
        } else {
            AppUser appUser = appUserOptional.get();
            if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
                throw new GeneralException("password does not match");
            } else {
                return tokenService.createToken(appUser);
            }
        }
    }
}
