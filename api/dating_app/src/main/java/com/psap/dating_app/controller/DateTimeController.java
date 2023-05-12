package com.psap.dating_app.controller;

import com.psap.dating_app.model.Calendar;
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
    public ResponseEntity<Calendar> getRecommendation(@PathVariable("id") long id) {
        return new ResponseEntity<>(dateTimeService.getRecommendation(), HttpStatus.OK);
    }
}
