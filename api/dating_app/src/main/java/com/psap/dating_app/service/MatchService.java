package com.psap.dating_app.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;


import org.hibernate.grammars.hql.HqlParser.CurrentDateFunctionContext;
import org.springframework.dao.DataAccessException;


import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;


import com.psap.dating_app.model.Couple;
import com.psap.dating_app.model.User;
import com.psap.dating_app.model.Chat;

import com.psap.dating_app.repository.UserRepository;
import com.psap.dating_app.repository.CoupleRepository;
import com.psap.dating_app.repository.ChatRepository;

import com.psap.dating_app.model.enums.SearchGender;
import com.psap.dating_app.model.enums.LoveLanguages;
import com.psap.dating_app.model.enums.StarSign;
import com.psap.dating_app.model.enums.MatchPurpose;
import com.psap.dating_app.model.enums.CoupleStatus;
import java.util.*;

@AllArgsConstructor
@Service
public class MatchService {
    private final CoupleRepository coupleRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

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

    public List<User> getUnmatchesAndDislikes(long id) {
        return userRepository.getUnmatchesAndDislikes(id);
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

    public Couple getCouple(long firstUser, long secondUser) {
        return coupleRepository.getCouple(firstUser, secondUser);
    }

    public User getUser(long currentUserId, Couple couple) {
        return userRepository.findById(couple.getFirst() == currentUserId ? couple.getSecond() : couple.getFirst());
    }

    public List<User> filterUsers(User user, List<User> allUsers, List<User> blacklist) {
        List<User> filteredUsers = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            User current = allUsers.get(i);
            if (current.getId() == user.getId()) continue;
            if (user.getSearchGender().toString().equals(current.getGender().toString()) || user.getSearchGender().equals(SearchGender.ANY)) {
                if (user.getRadius() <= current.getRadius() && !blacklist.contains(current)) {
                    filteredUsers.add(current);
                }
            }
        }
         return filteredUsers;
    }
    
    public Couple addRecommendation(Couple couple) {
        return coupleRepository.save(couple);
    }

    public Chat createChat() {
        return chatRepository.save(new Chat());
    }


    public double calculateUserWeights(User current, User user) {
        double ageDifferenceScore;
        double personalityTypeScore;
        double loveLanguageScore;
        double matchPurposeScore;
        double starSignScore;

        double weightAgeDifference = 0.1;
        double weightPersonalityType = 0.3;
        double weightLoveLanguage = 0.3;
        double weightMatchPurpose = 0.2;
        double weightStarSign = 0.1;

        LocalDate localDate1 = current.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(localDate1, localDate2);
        int ageDifference = period.getYears();
        ageDifferenceScore = 1.0 - (ageDifference / 100.0);

        Map<SimpleEntry<LoveLanguages, LoveLanguages>, Double> scores = new HashMap<>();
        scores.put(new SimpleEntry<>(LoveLanguages.ACTS_OF_SERVICE, LoveLanguages.RECEIVING_GIFTS), 0.5);
        scores.put(new SimpleEntry<>(LoveLanguages.RECEIVING_GIFTS, LoveLanguages.ACTS_OF_SERVICE), 0.5);
        scores.put(new SimpleEntry<>(LoveLanguages.PHYSICAL_TOUCH, LoveLanguages.QUALITY_TIME), 0.5);
        scores.put(new SimpleEntry<>(LoveLanguages.QUALITY_TIME, LoveLanguages.PHYSICAL_TOUCH), 0.5);
        scores.put(new SimpleEntry<>(LoveLanguages.WORDS_OF_AFFIRMATION, LoveLanguages.QUALITY_TIME), 0.5);
        scores.put(new SimpleEntry<>(LoveLanguages.QUALITY_TIME, LoveLanguages.WORDS_OF_AFFIRMATION), 0.5);
        
        if (current.getLoveLanguage().toString().equals(user.getLoveLanguage().toString())) {
            loveLanguageScore = 0.8;
        } else {
            SimpleEntry<LoveLanguages, LoveLanguages> loveLanguagesPair = new SimpleEntry<>(current.getLoveLanguage(), user.getLoveLanguage());
            loveLanguageScore = scores.getOrDefault(loveLanguagesPair, 0.1);
        }


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
        
        if (current.getMatchPurpose().equals(user.getMatchPurpose())) {
            matchPurposeScore = 1;
        }
        else {
            matchPurposeScore = 0.5;
        }

        double totalScore = weightAgeDifference * ageDifferenceScore + weightPersonalityType * personalityTypeScore + weightLoveLanguage * loveLanguageScore + weightMatchPurpose * matchPurposeScore + weightStarSign * starSignScore;

        return totalScore;
    }

    public Couple getRecommendation(long id) {
        return coupleRepository.getRecommendation(id);
    }

    public List<User> getRecommendations(long id) {
        return userRepository.getRecommendations(id);
    }

    public int setDislike(long coupleId) {
        return coupleRepository.setDislike(coupleId);
    }

    public int setUndecided(long coupleId) {
        return coupleRepository.setUndecided(coupleId);
    }

    public int setLike(long coupleId) {
        return coupleRepository.setLike(coupleId);
    }

    public int deleteAllDislikes() {
        return coupleRepository.deleteAllDislikes();
    }

    public Couple checkMatch(long currentUserId, long otherUserId) {
        return coupleRepository.checkMatch(currentUserId, otherUserId);
    }

    public boolean updateCoupleStatus(Couple couple) {
        if (couple.getStatus().equals(CoupleStatus.RECOMMENDED)) {
            couple.setStatus(CoupleStatus.UNDECIDED);
            coupleRepository.save(couple);
            return false;
        }
        else if ((couple.getStatus().equals(CoupleStatus.UNDECIDED))) {
            couple.setStatus(CoupleStatus.LIKES);
            coupleRepository.save(couple);
            return true;
        }
        else return false;
    }

}
