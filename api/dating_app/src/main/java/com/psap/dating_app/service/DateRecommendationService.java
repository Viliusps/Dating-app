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
    public DateRecommendation getDateRecommendation(long userId, String locationOption) {
        DateRecommendation recommendation = new DateRecommendation();

        Map<String, String> userLocation = getUserLocation();
        Map<String, String> matchLocation = getUserLocation();

        List<Map<String, String>> route = getRouteDataFromUserToMatch(userLocation, matchLocation);
        Map<String, String> midpointLocation = calculateMidpointFromRoute(route);

        if (locationOption == "yourLocation") {
            recommendation.lattitude = userLocation.get("lattitude");
            recommendation.longitude = userLocation.get("longitude");
        }
        else if (locationOption == "matchLocation") {
            recommendation.lattitude = matchLocation.get("lattitude");
            recommendation.lattitude = matchLocation.get("longitude");
        }
        else if (locationOption == "matchLocation") {
            recommendation.lattitude = midpointLocation.get("lattitude");
            recommendation.lattitude = midpointLocation.get("longitude");
        }

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
