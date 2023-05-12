package com.psap.dating_app.service.MockAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import com.psap.dating_app.model.Event;

public class CalendarAPI {

    public static List<Event> getEvents(long userId) {
        List<Event> results = new ArrayList();
        results = getDataSet(userId);
        return results;
    }

    private static String getRandomLatCoords() {
        Random random = new Random();
        double minLat = 53.9014;
        double maxLat = 56.4521;
        String lat = Double.toString(minLat + (maxLat - minLat) * random.nextDouble());
        return lat;
    }

    private static String getRandomLonCoords() {
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
        Date randomDate = new Date(startDate.getTime() + (long)(range * random.nextDouble()));
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
}
