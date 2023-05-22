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
@RequestMapping("/api/v1/dateRecommendations")
public class DateRecommendationController {
    DateRecommendationService dateRecommendationService;

    @GetMapping("/{id}")
    public ResponseEntity<List<DateRecommendation>> getDateRecommendation(@PathVariable("id") long userId) {
        Map<String, String> userLocation = dateRecommendationService.getUserLocation();
        Map<String, String> matchLocation = dateRecommendationService.getUserLocation();        
        
        return new ResponseEntity<>(dateRecommendationService.getDateRecommendation(userId), HttpStatus.OK);
    }

    // @GetMapping("/getUserLocation")
    // public ResponseEntity<List<DateRecommendation>> getUserLocation(@RequestBody UserLocationRequest request) {
    //     return DateRecommendationService.getUserLocation(request.getUserId());
    // }

}
