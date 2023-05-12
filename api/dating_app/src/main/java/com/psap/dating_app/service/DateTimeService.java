package com.psap.dating_app.service;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.psap.dating_app.model.Event;
import com.psap.dating_app.repository.EventRepository;

@AllArgsConstructor
@Service
public class DateTimeService {
    private final EventRepository eventRepository;

    public Event getRecommendation(long userId){
        Event event = new Event();
        List<Event> userCalendar = eventRepository.getByUserId(userId);

        userCalendar = assignInitialValues(userCalendar);

        return event;
    }

    private List<Event> assignInitialValues(List<Event> userCalendar) {

        return userCalendar;
    }

    private void selectNew() {

    }

    private void selectPopular() {

    }

    private void selectRandom() {

    }

    private void checkIfMatch() {

    }

    public void compareTimes() {

    }

    public void saveVote() {
        
    }

}
