package com.psap.dating_app.controller;

import com.psap.dating_app.model.Event;
import com.psap.dating_app.model.requests.CompareTimesRequest;
import com.psap.dating_app.service.DateTimeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/time")
public class DateTimeController {
    DateTimeService dateTimeService;

    @GetMapping
    public ResponseEntity<Event> getRecommendation(@PathVariable("userId") long userId) {
        return new ResponseEntity<>(dateTimeService.getRecommendation(userId), HttpStatus.OK);
    }

    @PostMapping("/compare")
    public void compareTimes(@Valid @RequestBody CompareTimesRequest request) {
        dateTimeService.compareTimes(request.getSelectedTimes(), request.getRecommendedTimes());
    }
}
