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
import java.util.ArrayList;



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
    public ResponseEntity<List<User>> getMatch(@PathVariable("id") long id) {
        User currentUser = matchService.getCurrentUser(id);
        Couple couple = matchService.getMatch(id);
        List<User> matches = new ArrayList<User>();
        if(validate(couple))  {
            matches.add(matchService.getUser(currentUser.getId(), couple));
            return new ResponseEntity<>(matches, HttpStatus.OK);
        }

        List<Couple> undecidedCouples = matchService.getUndecidedCouple(id);
        if (undecidedCouples.size() != 0) {
            Couple undecidedCouple = undecidedCouples.get(0);
            if(validate(undecidedCouple))  {
                matches.add(matchService.getUser(currentUser.getId(), undecidedCouple));
                return new ResponseEntity<>(matches, HttpStatus.OK);
            }
        }

        matchService.deleteAllRecommendations(id);
        List<User> allUsers = matchService.getUsers();
        List<User> blacklist = matchService.getUnmatchesAndDislikes(id);
        List<User> filteredUsers = matchService.filterUsers(currentUser, allUsers, blacklist);
        if (filteredUsers.isEmpty()) {
            return new ResponseEntity<>(matches, HttpStatus.NOT_FOUND);
        }
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
        List<User> recommendations = matchService.getRecommendations(id);


        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }

    @GetMapping("/setDislike/user/{userId}/otherUser/{otherUserId}")
    public ResponseEntity<List<User>> setDislike(@PathVariable("userId") long currentUserId, @PathVariable("otherUserId") long otherUserId) {
        Couple couple = matchService.getCouple(currentUserId, otherUserId);
        long coupleId = couple.getId();
        List<User> matches = new ArrayList<User>();
        if (matchService.setDislike(coupleId) == 0) {
            return new ResponseEntity<>(matches, HttpStatus.NOT_FOUND);
        } 
        List<User> allRecommendations = matchService.getRecommendations(currentUserId);
        if (allRecommendations.isEmpty()) return new ResponseEntity<>(matches, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(allRecommendations, HttpStatus.OK);
    }

    @GetMapping("/setLike/user/{userId}/otherUser/{otherUserId}")
    public ResponseEntity<Boolean> setLike(@PathVariable("userId") long currentUserId, @PathVariable("otherUserId") long otherUserId) {
        Couple couple = matchService.getCouple(currentUserId, otherUserId);
        return new ResponseEntity<>(matchService.updateCoupleStatus(couple), HttpStatus.OK);
    }

    @GetMapping("/deleteAllDislikes")
    public ResponseEntity<Integer> deleteAllDislikes() {
        return new ResponseEntity<>(matchService.deleteAllDislikes(), HttpStatus.OK);
    }
}
