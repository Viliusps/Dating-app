package com.psap.dating_app.service;

import java.rmi.ServerException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.psap.dating_app.model.Event;
import com.psap.dating_app.repository.EventRepository;
import com.psap.dating_app.service.MockAPI.CalendarAPI;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SyncService {
    EventRepository eventRepository;

    public void sync(long userId) throws ServerException {
        List<Event> events = CalendarAPI.getEvents(userId);
        if(checkReply(events)) {
            eventRepository.deleteAllPreviousEvents(userId);
            eventRepository.saveAll(events);
        } else {
            throw new ServerException("Calendar API threw an error!");
        }
    }

    //Since it's mock data, it will always be correct
    private Boolean checkReply(List<Event> events) {
        return true;
    }
    
}