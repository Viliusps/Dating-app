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
import com.psap.dating_app.model.Chat;
import com.psap.dating_app.model.enums.CoupleStatus;
import com.psap.dating_app.service.MatchService;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Date;
import java.util.List;
import java.util.Comparator;



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
        if(validate(couple))  return new ResponseEntity<>(couple, HttpStatus.OK);

        List<Couple> undecidedCouples = matchService.getUndecidedCouple(id);
        if (undecidedCouples.size() != 0) {
            Couple undecidedCouple = undecidedCouples.get(0);
            if(validate(undecidedCouple))  return new ResponseEntity<>(undecidedCouple, HttpStatus.OK);
        }

        matchService.deleteAllRecommendations(id);
        List<User> allUsers = matchService.getUsers();
        User currentUser = matchService.getCurrentUser(id);
        List<User> blacklist = matchService.getUnmatchesAndDislikes(id);
        List<User> filteredUsers = matchService.filterUsers(currentUser, allUsers, blacklist);
        if (filteredUsers.isEmpty()) return new ResponseEntity<>(new Couple(), HttpStatus.NOT_FOUND);
        int i = filteredUsers.size() - 1;
        while(i >= 0) {
            User recommendedUser = filteredUsers.get(i);
            double weights = matchService.calculateUserWeights(currentUser, recommendedUser);
            filteredUsers.remove(i);
            i--;
            Couple newRecommendation = new Couple();
            newRecommendation.setStatus(CoupleStatus.RECOMMENDED);
            newRecommendation.setDate(new Date());
            newRecommendation.setWeightDiff(weights);
            newRecommendation.setFirst(currentUser.getId());
            newRecommendation.setSecond(recommendedUser.getId());
            Chat chat = matchService.createChat();
            newRecommendation.setChat(chat.getId());
            matchService.addRecommendation(newRecommendation);
        }
        Couple recommendation = matchService.getRecommendation(id);
        return new ResponseEntity<>(recommendation, HttpStatus.OK);
    }

    @GetMapping("/setDislike/user/{userId}/couple/{coupleId}")
    public ResponseEntity<Couple> setDislike(@PathVariable("userId") long userId, @PathVariable("coupleId") long coupleId) {
        if (matchService.setDislike(coupleId) == 0) return new ResponseEntity<>(new Couple(), HttpStatus.NOT_FOUND);
        List<Couple> allRecommendations = matchService.getRecommendations(coupleId);
        if (allRecommendations.isEmpty()) return new ResponseEntity<>(new Couple(), HttpStatus.NOT_FOUND);
        Couple recommendation = allRecommendations.stream().max(Comparator.comparing(Couple::getWeightDiff)).orElse(null);
        return new ResponseEntity<>(recommendation, HttpStatus.OK);
    }

    @GetMapping("/setLike/user/{userId}/couple/{coupleId}")
    public ResponseEntity<Couple> setLike(@PathVariable("userId") long userId, @PathVariable("coupleId") long coupleId) {
        if (matchService.setLike(coupleId) == 0) return new ResponseEntity<>(new Couple(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new Couple(), HttpStatus.OK);
    }
}
