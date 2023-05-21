package com.psap.dating_app.service;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.psap.dating_app.model.Couple;
import com.psap.dating_app.model.Event;
import com.psap.dating_app.model.User;
import com.psap.dating_app.model.enums.EventType;
import com.psap.dating_app.model.responses.DateTimePageResponse;
import com.psap.dating_app.repository.CoupleRepository;
import com.psap.dating_app.repository.EventRepository;
import com.psap.dating_app.repository.MessageRepository;
import com.psap.dating_app.repository.UserRepository;

@AllArgsConstructor
@Service
public class DateTimeService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CoupleRepository coupleRepository;
    private final MessageRepository messageRepository;

    public List<Date> getRecommendation(long userId){
        Integer earliestHour = 11;
        Integer latestHour = 20;
        List<Date> recommendations = new ArrayList<>();

        Couple couple = coupleRepository.findCurrentCoupleByUserId(userId);

        User user = userRepository.findById(couple.getFirst());
        User matchedWith = userRepository.findById(couple.getSecond());

        
        List<Event> userCalendar = eventRepository.getByUserId(user.getId());
        List<Event> secondUserCalendar = eventRepository.getByUserId(matchedWith.getId());

        Map<Date, Integer> intialValues = assignInitialValues(userCalendar, secondUserCalendar, earliestHour, latestHour);

        if(eventRepository.getDatesByUser(user.getId()).size() > 0 || eventRepository.getDatesByUser(matchedWith.getId()).size() > 0){ //had any dates
            List<Event> firstUsersDates = eventRepository.getDatesByUser(user.getId());
            List<Event> secondUserDates = eventRepository.getDatesByUser(matchedWith.getId());
            recommendations = selectNew(firstUsersDates, secondUserDates, intialValues);
        }
        else {
            if(eventRepository.getAllDates().size() > 0) { //any dates in the system
                List<Couple> allCouples = coupleRepository.getAllCouples();
                recommendations = selectPopular(allCouples);
            }
            else {
                recommendations = selectRandom(intialValues);
            }
        }
        Calendar calendar = Calendar.getInstance();
        for (Date date : recommendations) {
            calendar.setTime(date);
            int minutes = calendar.get(Calendar.MINUTE);
            int roundedMinutes = Math.round(minutes / 5) * 5;
            calendar.set(Calendar.MINUTE, roundedMinutes);
            calendar.set(Calendar.SECOND, 0);
            Date roundedDate = calendar.getTime();
            recommendations.set(recommendations.indexOf(date), roundedDate);
        }
        return recommendations;
    }

    private Map<Date, Integer> assignInitialValues(List<Event> userCalendar1, List<Event> userCalendar2, Integer earliestHour, Integer latestHour) {
        Map<Date, Integer> dateTimeMap = new HashMap<>();
        Date now = new Date();
        Date end = new Date(now.getTime() + TimeUnit.DAYS.toMillis(7));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        while (calendar.getTime().before(end)) {
            Date currentHour = calendar.getTime();
            if (calendar.get(Calendar.HOUR_OF_DAY) >= 11 && calendar.get(Calendar.HOUR_OF_DAY) <= 20) {
                dateTimeMap.put(currentHour, 0);
            }
            calendar.add(Calendar.HOUR_OF_DAY, 1);
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

    private List<Date> selectNew(List<Event> first, List<Event> second, Map<Date, Integer> initialValues) {
        List<Date> results = new ArrayList<Date>(5);

        for (Map.Entry<Date, Integer> entry : initialValues.entrySet()) {
            Date mapKey = entry.getKey();

            for (Event event : first) {
                Date date = event.getStartDate();
                if (isSameDayOfWeekAndTime(mapKey, date)) {
                    entry.setValue(entry.getValue() + 1);
                }
            }

            for (Event event : second) {
                Date date = event.getStartDate();
                if (isSameDayOfWeekAndTime(mapKey, date)) {
                    entry.setValue(entry.getValue() + 1);
                }
            }
        }
        List<Map.Entry<Date, Integer>> entries = new ArrayList<>(initialValues.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (int i = 0; i < 5 && i < entries.size(); i++) {
            results.add(entries.get(i).getKey());
        }

        return results;
    }

    private boolean isSameDayOfWeekAndTime(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.DAY_OF_WEEK) == calendar2.get(Calendar.DAY_OF_WEEK)
                && calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY)
                && calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE);
    }

    private List<Date> selectPopular(List<Couple> allCouples) {
        List<Date> allTimes = new ArrayList<Date>();
        for(Couple couple : allCouples) {
            User first = userRepository.findById(couple.getFirst());
            User second = userRepository.findById(couple.getSecond());

            List<Event> firstUserDates = eventRepository.getDatesByUser(first.getId());
            List<Event> secondUserDates = eventRepository.getDatesByUser(second.getId());

            for(Event event : firstUserDates) {
                allTimes.add(event.getStartDate());
            }
            for(Event event : secondUserDates) {
                allTimes.add(event.getStartDate());
            }
        }

        Map<Date, Integer> frequencyMap = new HashMap<>();

        for (Date date : allTimes) {
            frequencyMap.put(date, frequencyMap.getOrDefault(date, 0) + 1);
        }

        PriorityQueue<Map.Entry<Date, Integer>> pq = new PriorityQueue<>(
                (a, b) -> a.getValue().equals(b.getValue()) ? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue()
        );

        for (Map.Entry<Date, Integer> entry : frequencyMap.entrySet()) {
            pq.offer(entry);
        }

        List<Date> mostFrequentDates = new ArrayList<>();
        while (mostFrequentDates.size() < 5 && !pq.isEmpty()) {
            mostFrequentDates.add(pq.poll().getKey());
        }

        return mostFrequentDates;
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

    private List<Date> checkIfMatch(List<Date> selectedTimes, List<Date> recommendedTimes) {
        List<Date> matching = new ArrayList<Date>();
        for (Date element : recommendedTimes) {
            if (!selectedTimes.contains(element)) {
                matching.add(element);
            }
        }
        return matching;
    }

    public DateTimePageResponse compareTimes(List<Date> selectedTimes, List<Date> recommendedTimes, Integer userId) {
        DateTimePageResponse response = new DateTimePageResponse();

        List<Date> matches = checkIfMatch(selectedTimes, recommendedTimes);
        Couple couple = coupleRepository.findCurrentCoupleByUserId(userId);

        User user = userRepository.findById(couple.getFirst());
        User matchedWith = userRepository.findById(couple.getSecond());
        ZoneId vilniusTimeZone = ZoneId.of("Europe/Vilnius");
        LocalDate currentDate = LocalDate.now(vilniusTimeZone);
        Date date = Date.from(currentDate.atStartOfDay(vilniusTimeZone).toInstant());

        long chatId = couple.getChat();

        if(matches.size() > 1) {
            messageRepository.notify(chatId, userId, "Multiple times found. Time selection is now open!", date);
            response.setPage("VOTE");
            response.setDates(matches);

        }

        else if(matches.size() == 1) {
            Date startDate = matches.get(0);
            eventRepository.saveDateTime(startDate, user.getName() + "'s and " + matchedWith.getName() + "'s date", user.getId(), EventType.DATE.name());
            eventRepository.saveDateTime(startDate, user.getName() + "'s and " + matchedWith.getName() + "'s date", matchedWith.getId(), EventType.DATE.name());
            messageRepository.notify(chatId, userId, "Your planned date is happening on " + startDate, date);
            response.setPage("OPTIONS");
        }
        return response;
    }

    public DateTimePageResponse saveVote(Date date, Integer userId) {
        DateTimePageResponse response = new DateTimePageResponse();
        ZoneId vilniusTimeZone = ZoneId.of("Europe/Vilnius");
        LocalDate currentDate = LocalDate.now(vilniusTimeZone);
        Date dateStamp = Date.from(currentDate.atStartOfDay(vilniusTimeZone).toInstant());
        Couple couple = coupleRepository.findCurrentCoupleByUserId(userId);

        User user = userRepository.findById(couple.getFirst());
        User matchedWith = userRepository.findById(couple.getSecond());

        long chatId = couple.getChat();

        eventRepository.saveDateTime(date, user.getName() + "'s and " + matchedWith.getName() + "'s date", user.getId(), EventType.DATE.name());
        eventRepository.saveDateTime(date, user.getName() + "'s and " + matchedWith.getName() + "'s date", matchedWith.getId(), EventType.DATE.name());
        messageRepository.notify(chatId, userId, "Your planned date is happening on " + date, dateStamp);
        response.setPage("OPTIONS");
        return response;
    }

    private static Date truncateToHour(Date dateTime) {
        return new Date(dateTime.getTime() / TimeUnit.HOURS.toMillis(1) * TimeUnit.HOURS.toMillis(1));
    }

}
