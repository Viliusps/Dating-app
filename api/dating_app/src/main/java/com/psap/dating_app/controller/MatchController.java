package com.psap.dating_app.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.psap.dating_app.model.Couple;
import com.psap.dating_app.model.User;
import com.psap.dating_app.service.MatchService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {
    MatchService matchService;

    public boolean validate(Couple couple) {
        return couple == null ? false : true;
    }

    public boolean checkIfListIsEmpty(List<User> list) {
        return list.isEmpty();
    }


    @GetMapping("/getMatch/{id}")
    public ResponseEntity<Couple> getMatch(@PathVariable("id") long id) {
        Couple couple = matchService.getMatch(id);
        if(validate(couple)) new ResponseEntity<>(couple, HttpStatus.OK);

        List<Couple> undecidedCouples = matchService.getUndecidedCouple(id);
        if (undecidedCouples.size() != 0) {
            Couple undecidedCouple = undecidedCouples.get(0);
            if(validate(undecidedCouple)) new ResponseEntity<>(undecidedCouple, HttpStatus.OK);
        }


        matchService.deleteAllRecommendations(id);
        List<User> allUsers = matchService.getUsers();
        User currentUser = matchService.getCurrentUser(id);
        List<Couple> result = matchService.getUnmatchesAndDislikes(id);
        List<User> blacklist = new ArrayList<>();
        for (int i = 0;  i < result.size(); i++) {
            blacklist.add(matchService.getCurrentUser(result.get(i).getId()));
        }
        List<User> filteredUsers = matchService.filterUsers(currentUser, allUsers, blacklist);
        if (filteredUsers.isEmpty()) return new ResponseEntity<>(new Couple(), HttpStatus.NOT_FOUND);
        int i = filteredUsers.size() - 1;
        while(i >= 0) {
            double weights = matchService.calculateUserWeights(currentUser, filteredUsers.get(i));
            filteredUsers.remove(i);
            i--;
        }
        return new ResponseEntity<>(matchService.getMatch(id), HttpStatus.OK);
    }
}
