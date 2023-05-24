package com.psap.dating_app.service.MockAPI;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GoogleMapsAPI {
    
    public static List<Map<String, String>> getRouteVector(Map<String, String> source,
            Map<String, String> destination) {
        // Mocked behavior to return a sample route vector
        List<Map<String, String>> routeVector = new ArrayList<>();

        String sourceLon = source.get("longitude");
        String sourceLat = source.get("lattitude");

        String destinationLon = destination.get("longitude");
        String destinationLat = destination.get("lattitude");

        Map<String, String> sourceCoordinate = new HashMap<>();
        sourceCoordinate.put("latitude", sourceLat);
        sourceCoordinate.put("longitude", sourceLon);

        // Create destination coordinate map
        Map<String, String> destinationCoordinate = new HashMap<>();
        destinationCoordinate.put("latitude", destinationLat);
        destinationCoordinate.put("longitude", destinationLon);

        // Add coordinates to the route vector
        routeVector.add(sourceCoordinate);
        routeVector.add(destinationCoordinate);

        return routeVector;
    }

    public static Map<String, String> calculateMidpointLocation(List<Map<String, String>> routeVector) {
        double totalLat = 0.0;
        double totalLon = 0.0;

        for (Map<String, String> coordinate : routeVector) {
            String latitude = coordinate.get("lattitude");
            String longitude = coordinate.get("longitude");

            totalLat += Double.parseDouble(latitude);
            totalLon += Double.parseDouble(longitude);
        }

        double averageLat = totalLat / routeVector.size();
        double averageLon = totalLon / routeVector.size();

        Map<String, String> midpoint = new HashMap<>();
        midpoint.put("lattitude", Double.toString(averageLat));
        midpoint.put("longitude", Double.toString(averageLon));

        return midpoint;
    }

    public static Map<String, Map<String, String>> getBigCitiesIn50KmRange(Map<String, String> location) {
        List<String> cityNames = Arrays.asList("Gargzdai", "Kretinga", "Plunge", "Klaipeda", "Palanga");

        Map<String, Map<String, String>> bigCities = new HashMap<>();

        double sourceLat = Double.parseDouble(location.get("latitude"));
        double sourceLon = Double.parseDouble(location.get("longitude"));

        Random random = new Random();

        for (String cityName : cityNames) {
            // determine a random direction by calculating an angle
            double angle = random.nextDouble() * 2 * Math.PI;
            // pick a random distance
            double distance = random.nextDouble() * 50.0 / 111.2; // 111.2 is an approximate degrees-to-kilometers
                                                                  // conversion

            double newLat = sourceLat + distance * Math.cos(angle);
            double newLon = sourceLon + distance * Math.sin(angle);

            Map<String, String> cityCoordinates = new HashMap<>();
            cityCoordinates.put("latitude", String.valueOf(newLat));
            cityCoordinates.put("longitude", String.valueOf(newLon));

            bigCities.put(cityName, cityCoordinates);
        }

        return bigCities;
    }

    public static Map<String, Map<String, String>> parseCities(Map<String, String> location) {
        return getBigCitiesIn50KmRange(location);
    }

    public static List<String> getCityPlacesWithDateTypeAndTime() {
        return new ArrayList<>();
    }
}
