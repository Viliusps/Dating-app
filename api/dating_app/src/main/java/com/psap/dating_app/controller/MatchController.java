package com.psap.dating_app.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.psap.dating_app.model.Couple;
import com.psap.dating_app.service.MatchService;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {
    MatchService matchService;

    public boolean checkIfEmpty(Couple couple) {
        return couple == null ? true : false;
    }

    @GetMapping("/getMatch/{id}")
    public ResponseEntity<Couple> getMatch(@PathVariable("id") long id) {
        Couple couple = matchService.getMatch(id);
        if(!checkIfEmpty(couple)) new ResponseEntity<>(matchService.getMatch(id), HttpStatus.OK);
        matchService.getUndecidedCouple(id);
        return new ResponseEntity<>(matchService.getMatch(id), HttpStatus.OK);
    }
}
