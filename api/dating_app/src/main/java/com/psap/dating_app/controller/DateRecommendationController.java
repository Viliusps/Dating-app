package com.psap.dating_app.controller;

import java.util.List;
import java.util.Map;

import com.psap.dating_app.model.DateRecommendation;
import com.psap.dating_app.service.DateRecommendationService;
import com.psap.dating_app.service.DateRecommendationService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/dateRecommendation")
public class DateRecommendationController {
    DateRecommendationService dateRecommendationService;

    // @GetMapping("/{userId}")
    // public ResponseEntity<DateRecommendation> getDateRecommendation(@PathVariable("userId") long userId) {        
    //     return new ResponseEntity<>(dateRecommendationService.getDateRecommendation(userId), HttpStatus.OK);
    // }

    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<DateRecommendation> getDateRecommendation(@PathVariable("userId") long userId) {        
        System.out.println(new ResponseEntity<>(dateRecommendationService.getDateRecommendation(userId), HttpStatus.OK));
        return new ResponseEntity<>(dateRecommendationService.getDateRecommendation(userId), HttpStatus.OK);
    }
}
