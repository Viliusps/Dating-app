package com.psap.dating_app.service.MockAPI;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.Map;
import java.util.HashMap;

import com.psap.dating_app.model.Event;

public class CalendarAPI {

    public static List<Event> getEvents(long userId) {
        List<Event> results = new ArrayList();
        results = getDataSet(userId);
        return results;
    }

    public static String getRandomLatCoords() {
        Random random = new Random();
        double minLat = 53.9014;
        double maxLat = 56.4521;
        String lat = Double.toString(minLat + (maxLat - minLat) * random.nextDouble());
        return lat;
    }

    public static String getRandomLonCoords() {
        Random random = new Random();
        double minLon = 20.9551;
        double maxLon = 26.8355;
        String lon = Double.toString(minLon + (maxLon - minLon) * random.nextDouble());
        return lon;
    }

    private static Date getRandomDate() {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2023, Calendar.MAY, 12, 0, 0, 0);
        Date startDate = calendar.getTime();
        calendar.set(2023, Calendar.JUNE, 12, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();

        // Generate a random date between the start and end dates
        long range = endDate.getTime() - startDate.getTime();
        Date randomDate = new Date(startDate.getTime() + (long) (range * random.nextDouble()));
        return randomDate;

    }

    private static List<Event> getDataSet(long userId) {
        List<Event> results = new ArrayList();

        Date randomDate = getRandomDate();
        String lat = getRandomLatCoords();
        String lon = getRandomLonCoords();

        Event firstEvent = new Event();
        firstEvent.setEndDate(null);
        firstEvent.setLattitude(lat);
        firstEvent.setLongitude(lon);
        firstEvent.setStartDate(randomDate);
        firstEvent.setTitle("First event");
        firstEvent.setUserId(userId);

        randomDate = getRandomDate();
        lat = getRandomLatCoords();
        lon = getRandomLonCoords();

        Event secondEvent = new Event();
        secondEvent.setEndDate(null);
        secondEvent.setLattitude(lat);
        secondEvent.setLongitude(lon);
        secondEvent.setStartDate(randomDate);
        secondEvent.setTitle("Second event");
        secondEvent.setUserId(userId);

        randomDate = getRandomDate();
        lat = getRandomLatCoords();
        lon = getRandomLonCoords();

        Event thirdEvent = new Event();
        thirdEvent.setEndDate(null);
        thirdEvent.setLattitude(lat);
        thirdEvent.setLongitude(lon);
        thirdEvent.setStartDate(randomDate);
        thirdEvent.setTitle("Third event");
        thirdEvent.setUserId(userId);

        randomDate = getRandomDate();
        lat = getRandomLatCoords();
        lon = getRandomLonCoords();

        Event fourthEvent = new Event();
        fourthEvent.setEndDate(null);
        fourthEvent.setLattitude(lat);
        fourthEvent.setLongitude(lon);
        fourthEvent.setStartDate(randomDate);
        fourthEvent.setTitle("Fourth event");
        fourthEvent.setUserId(userId);

        results.add(firstEvent);
        results.add(secondEvent);
        results.add(thirdEvent);
        results.add(fourthEvent);

        return results;
    }

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

    public static Map<String, String> calculateMidpointLocation(List<List<String>> routeVector) {
        double totalLat = 0.0;
        double totalLon = 0.0;

        for (List<String> coordinate : routeVector) {
            String latitude = coordinate.get(0);
            String longitude = coordinate.get(1);

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
