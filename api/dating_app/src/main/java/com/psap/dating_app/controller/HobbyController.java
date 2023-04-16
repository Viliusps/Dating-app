package com.psap.dating_app.controller;
import java.util.List;

import com.psap.dating_app.model.Hobby;
import com.psap.dating_app.service.HobbyService;
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

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/hobbies")
public class HobbyController {
    HobbyService hobbyService;

    @GetMapping
    public ResponseEntity<List<Hobby>> getAllHobbies() {
        return new ResponseEntity<>(hobbyService.getAllHobbies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hobby> getHobbyById(@PathVariable("id") long id) {
        if (hobbyService.existsHobby(id)) {
            return new ResponseEntity<>(hobbyService.getHobbyById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Hobby> createUser(@Valid @RequestBody Hobby hobby) {
        return new ResponseEntity<>(hobbyService.createHobby(hobby), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hobby> updateUser(@PathVariable("id") long id, @Valid @RequestBody Hobby hobby) {
        if (hobbyService.existsHobby(id)) {
            return new ResponseEntity<>(hobbyService.updateHobby(id, hobby), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Hobby> deleteRoom(@PathVariable("id") Long id) {
        if (hobbyService.existsHobby(id)) {
            hobbyService.deleteHobby(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}