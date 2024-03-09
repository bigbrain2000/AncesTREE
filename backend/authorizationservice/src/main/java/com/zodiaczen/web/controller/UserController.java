package com.zodiaczen.web.controller;

import com.zodiaczen.service.user.UserService;
import com.zodiaczen.web.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1", produces = APPLICATION_JSON_VALUE)
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") @NotNull Integer id) {
        final User foundUser = userService.getById(id);
        return new ResponseEntity<>(foundUser, OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") @NotNull Integer id) {
        userService.deleteById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable @NotNull Integer id,
                                           @RequestBody @NotNull @Valid User user) {
        final User updateUser = userService.update(id, user);
        return new ResponseEntity<>(updateUser, OK);
    }
}
