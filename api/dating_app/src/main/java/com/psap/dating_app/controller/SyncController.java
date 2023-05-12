package com.psap.dating_app.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psap.dating_app.service.SyncService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/sync")
public class SyncController {
    SyncService syncService;

    @GetMapping("/{id}")
    public ResponseEntity<?> sync(@PathVariable("id") long userId) {
        try {
            syncService.sync(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during sync: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
