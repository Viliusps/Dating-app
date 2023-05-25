package com.psap.dating_app.controller;
import java.util.List;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psap.dating_app.model.User;
import com.psap.dating_app.model.requests.LoginRequest;
import com.psap.dating_app.service.UserService;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class AuthController {
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        if (userService.existsUser(id)) {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> register(@Valid @RequestBody User user) throws IllegalAccessException {
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequest request) {
        try{


            
            return new ResponseEntity<>(userService.login(request), HttpStatus.CREATED);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {
        if (userService.existsUser(id)) {
            return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        if (userService.existsUser(id)) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}