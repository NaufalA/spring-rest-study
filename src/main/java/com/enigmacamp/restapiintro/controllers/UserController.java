package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.UserData;
import com.enigmacamp.restapiintro.services.interfaces.UserService;
import com.enigmacamp.restapiintro.shared.classes.CommonResponse;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(
                HttpStatus.OK.toString(),
                HttpStatus.OK.getReasonPhrase(),
                userService.getAll()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable("id") Integer id) {
        Optional<UserData> user = userService.getById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("User Not Found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(
                HttpStatus.OK.toString(),
                HttpStatus.OK.getReasonPhrase(),
                user.get()
        ));
    }
}
