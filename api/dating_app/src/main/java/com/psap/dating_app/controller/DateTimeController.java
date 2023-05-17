package com.psap.dating_app.controller;

import com.psap.dating_app.model.requests.CompareTimesRequest;
import com.psap.dating_app.model.requests.TimeVoteRequest;
import com.psap.dating_app.model.responses.DateTimePageResponse;
import com.psap.dating_app.service.DateTimeService;

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
        return new ResponseEntity<>(dateTimeService.getRecommendation(userId), HttpStatus.OK);
    }

    @PostMapping("/compare")
    public DateTimePageResponse compareTimes(@RequestBody CompareTimesRequest request) {
        return dateTimeService.compareTimes(request.getSelectedDates(), request.getRecommendedDates(), request.getUserId());
    }

    @PostMapping("/vote")
    public DateTimePageResponse vote(@RequestBody TimeVoteRequest request) {
        return dateTimeService.saveVote(request.getDate(), request.getUserId());
    }
}
