package com.psap.dating_app.controller;

import com.psap.dating_app.model.requests.CompareTimesRequest;
import com.psap.dating_app.service.DateTimeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/time")
public class DateTimeController {
    DateTimeService dateTimeService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Date>> getRecommendation(@PathVariable("id") long userId) {
        System.out.println("Controller");
        return new ResponseEntity<>(dateTimeService.getRecommendation(userId), HttpStatus.OK);
    }

    @PostMapping("/compare")
    public void compareTimes(@Valid @RequestBody CompareTimesRequest request) {
        dateTimeService.compareTimes(request.getSelectedTimes(), request.getRecommendedTimes());
    }
}
