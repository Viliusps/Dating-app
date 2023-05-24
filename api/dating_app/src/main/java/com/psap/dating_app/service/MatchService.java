package com.psap.dating_app.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.grammars.hql.HqlParser.CurrentDateFunctionContext;
import org.springframework.dao.DataAccessException;


import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;


import com.psap.dating_app.model.Couple;
import com.psap.dating_app.model.User;

import com.psap.dating_app.repository.UserRepository;
import com.psap.dating_app.repository.CoupleRepository;

import com.psap.dating_app.model.enums.SearchGender;
import com.psap.dating_app.model.enums.LoveLanguages;
import com.psap.dating_app.model.enums.StarSign;
import com.psap.dating_app.model.enums.MatchPurpose;
import java.util.*;

@AllArgsConstructor
@Service
public class MatchService {
    private final CoupleRepository coupleRepository;
    private final UserRepository userRepository;

    private static final Map<StarSign, StarSign> OPPOSITE_SIGNS = new HashMap<StarSign, StarSign>() {{
        put(StarSign.ARIES, StarSign.LIBRA);
        put(StarSign.TAURUS, StarSign.SCORPIO);
        put(StarSign.GEMINI, StarSign.SAGITTARIUS);
        put(StarSign.CANCER, StarSign.CAPRICORN);
        put(StarSign.LEO, StarSign.AQUARIUS);
        put(StarSign.VIRGO, StarSign.PISCES);
        put(StarSign.LIBRA, StarSign.ARIES);
        put(StarSign.SCORPIO, StarSign.TAURUS);
        put(StarSign.SAGITTARIUS, StarSign.GEMINI);
        put(StarSign.CAPRICORN, StarSign.CANCER);
        put(StarSign.AQUARIUS, StarSign.LEO);
        put(StarSign.PISCES, StarSign.VIRGO);
    }};

    public List<Couple> getUnmatchesAndDislikes(long id) {
        return coupleRepository.getUnmatchesAndDislikes(id);
    }


    public Couple getMatch(long id) {
        Couple couple = coupleRepository.getMatch(id);
        return couple;
    }

    public List<Couple> getUndecidedCouple(long id) {
        return coupleRepository.getUndecidedCouples(id);
    }

    public boolean deleteAllRecommendations(long id) {
        try {
            coupleRepository.deleteAllRecommendations(id);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public List<User> getUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    public User getCurrentUser(long id) {
        return userRepository.findById(id);
    }

    public List<User> filterUsers(User user, List<User> allUsers, List<User> blacklist) {
        List<User> filteredUsers = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            User current = allUsers.get(i);
            if (current.getId() == user.getId()) continue;
            if (user.getSearchGender().toString().equals(current.getGender().toString()) || user.getSearchGender().equals(SearchGender.ANY)) {
                if (user.getRadius() == current.getRadius() && !blacklist.contains(current)) {
                    filteredUsers.add(current);
                }
            }
        }
         return filteredUsers;
    }

    public double calculateUserWeights(User current, User user) {
        double ageDifferenceScore;
        double personalityTypeScore;
        double loveLanguageScore;
        double matchPurposeScore;
        double starSignScore;


        LocalDate localDate1 = current.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(localDate1, localDate2);
        int ageDifference = period.getYears();
        ageDifferenceScore = 1.0 - (ageDifference / 100.0);

        if (current.getLoveLanguage().equals(user.getLoveLanguage())) {
            loveLanguageScore = 0.8;
        }
        else if ((current.getLoveLanguage().equals(LoveLanguages.ACTS_OF_SERVICE) && user.getLoveLanguage().equals(LoveLanguages.RECEIVING_GIFTS)) || 
        (current.getLoveLanguage().equals(LoveLanguages.RECEIVING_GIFTS) && user.getLoveLanguage().equals(LoveLanguages.ACTS_OF_SERVICE))) {
            loveLanguageScore = 0.5;
        }

        else if ((current.getLoveLanguage().equals(LoveLanguages.ACTS_OF_SERVICE) && user.getLoveLanguage().equals(LoveLanguages.RECEIVING_GIFTS)) || 
        (current.getLoveLanguage().equals(LoveLanguages.RECEIVING_GIFTS) && user.getLoveLanguage().equals(LoveLanguages.ACTS_OF_SERVICE))) {
            loveLanguageScore = 0.5;
        }

        else if ((current.getLoveLanguage().equals(LoveLanguages.PHYSICAL_TOUCH) && user.getLoveLanguage().equals(LoveLanguages.QUALITY_TIME)) || 
        (current.getLoveLanguage().equals(LoveLanguages.QUALITY_TIME) && user.getLoveLanguage().equals(LoveLanguages.PHYSICAL_TOUCH))) {
            loveLanguageScore = 0.5;
        }
        else loveLanguageScore = 0.1;

        if (current.getPersonalityType().equals(user.getPersonalityType())) {
            personalityTypeScore = 1.0;
        }
        
        else {
            int sharedPreferences = 0;
            for (int i = 0; i < 4; i++) {
                if (current.getPersonalityType().toString().charAt(i) == user.getPersonalityType().toString().charAt(i)) {
                    sharedPreferences++;
                }
            }

            if (sharedPreferences >= 3) {
                personalityTypeScore = 0.8;
            }
            else {
                personalityTypeScore = 0.5;
            }
        }


        if(OPPOSITE_SIGNS.get(current.getStarSign()).equals(user.getStarSign())) {
            starSignScore = 1.0;
        }
        
        else if (current.getStarSign().equals(user.getStarSign())) {
            starSignScore = 0.5;
        }

        else starSignScore = 0.2;
        
        if (current.getMatchPurpose().equals(MatchPurpose.LONG)) {

        }

        return 0.0;




        



    }
}
