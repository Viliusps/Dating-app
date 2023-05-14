package com.psap.dating_app.service;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.psap.dating_app.model.Couple;
import com.psap.dating_app.model.Event;
import com.psap.dating_app.model.User;
import com.psap.dating_app.repository.CoupleRepository;
import com.psap.dating_app.repository.EventRepository;
import com.psap.dating_app.repository.UserRepository;

@AllArgsConstructor
@Service
public class DateTimeService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CoupleRepository coupleRepository;

    public Event getRecommendation(long userId){
        Event event = new Event();

        Couple couple = coupleRepository.findCoupleByUser(userId);

        User user = userRepository.findById(couple.getFirst()).get();
        User matchedWith = userRepository.findById(couple.getSecond()).get();

        
        List<Event> userCalendar = eventRepository.getByUserId(user.getId());
        List<Event> secondUserCalendar = eventRepository.getByUserId(matchedWith.getId());

        Map<Date, Integer> intialValues = assignInitialValues(userCalendar, secondUserCalendar);

        List<Date> recommendations = new ArrayList<>();
        Boolean temp = false;

        if(temp){ //had any dates

            selectNew();

        }
        else {

            if(temp) { //any dates in the system

                selectPopular();

            }
            else {
                recommendations = selectRandom(intialValues);

            }

        }

        return event;
    }

    private Map<Date, Integer> assignInitialValues(List<Event> userCalendar1, List<Event> userCalendar2) {
        Map<Date, Integer> dateTimeMap = new HashMap<>();
        Date now = new Date();
        Date end = new Date(now.getTime() + TimeUnit.DAYS.toMillis(7));
        while (now.before(end)) {
            dateTimeMap.put(now, 0);
            now = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1));
        }

        for (Event event : userCalendar1) {
            Date startTime = event.getStartDate();
            Date endTime = event.getEndDate();
            dateTimeMap.remove(truncateToHour(startTime));
            if (endTime != null) {
                dateTimeMap.remove(truncateToHour(endTime));
            }
        }
        for (Event event : userCalendar2) {
            Date startTime = event.getStartDate();
            Date endTime = event.getEndDate();
             dateTimeMap.remove(truncateToHour(startTime));
            if (endTime != null) {
                dateTimeMap.remove(truncateToHour(endTime));
            }
        }

        return dateTimeMap;
    }

    private void selectNew() {

    }

    private void selectPopular() {

    }

    private List<Date> selectRandom(Map<Date, Integer> map) {

        Integer count = 5;
        List<Date> dates = new ArrayList<>(map.keySet());
        if (count > dates.size()) {
            throw new IllegalArgumentException("Cannot select more dates than available in map.");
        }
        List<Date> result = new ArrayList<>(count);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(dates.size());
            result.add(dates.remove(index));
        }
        return result;

    }

    private Boolean checkIfMatch(List<Date> selectedTimes, List<Date> recommendedTimes) {

        return true;
    }

    public void compareTimes(List<Date> selectedTimes, List<Date> recommendedTimes) {

        if(checkIfMatch(selectedTimes, recommendedTimes)) {

        }

    }

    public void saveVote() {
        
    }

    private static Date truncateToHour(Date dateTime) {
        return new Date(dateTime.getTime() / TimeUnit.HOURS.toMillis(1) * TimeUnit.HOURS.toMillis(1));
    }

}
