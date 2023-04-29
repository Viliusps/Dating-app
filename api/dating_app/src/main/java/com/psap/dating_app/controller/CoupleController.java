package com.psap.dating_app.controller;
import java.util.List;

import com.psap.dating_app.model.Couple;
import com.psap.dating_app.service.CoupleService;
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
@RequestMapping("/api/v1/couples")
public class CoupleController {
    CoupleService coupleService;

    @GetMapping
    public ResponseEntity<List<Couple>> getAllCouples() {
        return new ResponseEntity<>(coupleService.getAllCouples(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Couple> getCoupleById(@PathVariable("id") long id) {
        if (coupleService.existsCouple(id)) {
            return new ResponseEntity<>(coupleService.getCoupleById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Couple> createCouple(@Valid @RequestBody Couple couple) {
        return new ResponseEntity<>(coupleService.createCouple(couple), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Couple> updateCouple(@PathVariable("id") long id, @Valid @RequestBody Couple couple) {
        if (coupleService.existsCouple(id)) {
            return new ResponseEntity<>(coupleService.updateCouple(id, couple), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Couple> deleteCouple(@PathVariable("id") Long id) {
        if (coupleService.existsCouple(id)) {
            coupleService.deleteCouple(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byUser/{id}")
    public ResponseEntity<Couple> getCoupleByUserId(@PathVariable("id") long id) {
        return new ResponseEntity<>(coupleService.getCoupleByUserId(id), HttpStatus.OK);
    }
}