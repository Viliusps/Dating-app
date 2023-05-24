package com.psap.dating_app.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.psap.dating_app.model.DateRecommendation;
import com.psap.dating_app.model.Event;
import com.psap.dating_app.model.User;


import com.psap.dating_app.service.DateTimeService;
import com.psap.dating_app.service.MockAPI.CalendarAPI;
import com.psap.dating_app.service.MockAPI.GoogleMapsAPI;

import com.psap.dating_app.repository.UserRepository;


@AllArgsConstructor
@Service
public class DateRecommendationService {
    public DateRecommendation getDateRecommendation(long userId) {
        // jeigu yra sitas user id pas user1 arba user2, tuomet gaut date
        DateRecommendation recommendation = new DateRecommendation();

        Map<String, String> userLocation = getUserLocation();
        Map<String, String> matchLocation = getUserLocation();

        List<Map<String, String>> route = getRouteDataFromUserToMatch(userLocation, matchLocation);
        Map<String, String> midpoint = calculateMidpointFromRoute(route);
        // pagal diagrama, cia viska viduj reiktu kviest, bet keletas kvietimu turetu
        // but is front-endo, eventais, tai nededu per daug cia

        recommendation.lattitude = userLocation.get("lattitude");
        recommendation.longitude = userLocation.get("longitude");

        return recommendation;
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
        return GoogleMapsAPI.getRouteVector(userLocation, matchLocation);
    }

    public Map<String, String> calculateMidpointFromRoute(List<Map<String, String>> routeVector) {
        return GoogleMapsAPI.calculateMidpointLocation(routeVector);
    }

    public Map<String, Map<String, String>> getBigCitiesIn50KmRange(Map<String, String> location) {
        return GoogleMapsAPI.getBigCitiesIn50KmRange(location);
    }

    public List<String> getCityPlacesWithDateTypeAndTime() {
        return GoogleMapsAPI.getCityPlacesWithDateTypeAndTime();
    }
}
