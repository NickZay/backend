package ru.nickzay.todolist.signing;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nickzay.todolist.exceptions.GeneralException;


@RestController
@AllArgsConstructor
@RequestMapping
public class SignController {
    private final SignService signService;

    @PostMapping("/my_login")
    public ResponseEntity<String> login(@RequestBody Request request) {
        try {
            return new ResponseEntity<>(signService.signIn(request), HttpStatus.OK);
        } catch (GeneralException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody Request request) {
        try {
            return new ResponseEntity<>(signService.signUp(request), HttpStatus.CREATED);
        } catch (GeneralException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
