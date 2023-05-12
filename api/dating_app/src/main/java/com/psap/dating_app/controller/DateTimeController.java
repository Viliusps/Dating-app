package com.psap.dating_app.controller;

import com.psap.dating_app.model.Event;
import com.psap.dating_app.service.DateTimeService;
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
}
