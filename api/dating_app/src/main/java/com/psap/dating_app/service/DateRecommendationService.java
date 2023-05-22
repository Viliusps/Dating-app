package com.psap.dating_app.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.psap.dating_app.model.DateRecommendation;
import com.psap.dating_app.service.DateTimeService;
import com.psap.dating_app.repository.DatePlaceRecommendationRepository;
import com.psap.dating_app.service.MockAPI.CalendarAPI;

@AllArgsConstructor
@Service
public class DateRecommendationService {
    public List<DateRecommendation> getDateRecommendation(long userId) {
        // jeigu yra sitas user id pas user1 arba user2, tuomet gaut date
        List<DateRecommendation> recommendations = new ArrayList<>();

        // pagal diagrama, cia viska viduj reiktu kviest, bet keletas kvietimu turetu
        // but is front-endo, eventais, tai nededu per daug cia
        return recommendations;
    }

    public Map<String, String> getUserLocation() {
        Map<String, String> locationMap = new HashMap<>();

        String longitude = CalendarAPI.getRandomLonCoords();
        String lattitude = CalendarAPI.getRandomLatCoords();

        locationMap.put("longitude", longitude);
        locationMap.put("lattitude", lattitude);

        return locationMap;
    }

    public List<Map<String, String>> getRouteDataFromUserToMatch(Map<String, String> userLocation,
            Map<String, String> matchLocation) {
        return CalendarAPI.getRouteVector(userLocation, matchLocation);
    }

    public Map<String, String> calculateMidpointFromRoute(List<List<String>> routeVector) {
        return CalendarAPI.calculateMidpointLocation(routeVector);
    }

    public Map<String, Map<String, String>> getBigCitiesIn50KmRange(Map<String, String> location) {
        return CalendarAPI.getBigCitiesIn50KmRange(location);
    }

    public List<String> getCityPlacesWithDateTypeAndTime() {
        return CalendarAPI.getCityPlacesWithDateTypeAndTime();
    }



}
