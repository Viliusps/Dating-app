package com.psap.dating_app.controller;

import com.psap.dating_app.model.Question;
import com.psap.dating_app.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
    QuestionService questionService;

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") long id) {
        return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
    }
}
