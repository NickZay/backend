package ru.nickzay.todolist.token;

import ru.nickzay.todolist.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Optional<AppUser> findAppUserByToken(String token) {
        Optional<Token> confirmationToken = getToken(token);
        return confirmationToken.map(Token::getAppUser);
    }

    public String createToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        saveToken(
                new Token(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        appUser
                ));
        return token;
    }

    public String checkAndUpdateToken(String tokenString, AppUser appUser) {
        Optional<Token> tokenOptional = getToken(tokenString);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            if (token.getAppUser() != appUser) {
                throw new IllegalStateException("token is invalid");
            }
            if (Duration.between(LocalDateTime.now(), token.getExpiresAt()).toMinutes() >= 5) {
                return createToken(appUser);
            }
            return tokenString;
        }
        throw new IllegalStateException("token not found");
    }
}
