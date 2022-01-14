package ru.nickzay.todolist.token;

import ru.nickzay.todolist.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nickzay.todolist.exceptions.GeneralException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public AppUser findAppUserByToken(String token) throws GeneralException {
        Optional<Token> confirmationToken = getToken(token);
        if (confirmationToken.isPresent()) {
            return confirmationToken.get().getAppUser();
        }
        throw new GeneralException("user not found");
    }

    public String createToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        saveToken(
                new Token(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(180),
                        appUser
                ));
        return token;
    }

    public String checkAndUpdateToken(String tokenString, AppUser appUser) throws GeneralException {
        Optional<Token> tokenOptional = getToken(tokenString);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            if (token.getAppUser() != appUser) {
                throw new GeneralException("token is invalid");
            }
            if (Duration.between(LocalDateTime.now(), token.getExpiresAt()).toMinutes() <= 5) {
                return createToken(appUser);
            }
            return tokenString;
        }
        throw new GeneralException("token not found");
    }
}
