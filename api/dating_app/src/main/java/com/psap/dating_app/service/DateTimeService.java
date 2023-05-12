package com.psap.dating_app.service;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.psap.dating_app.model.Calendar;
import com.psap.dating_app.repository.DateTimeRepository;

@AllArgsConstructor
@Service
public class DateTimeService {
    private final DateTimeRepository dateTimeRepository;

    public Calendar getRecommendation(){
        Calendar calendar = new Calendar();

        return calendar;
    }

    private void assignInitialValues() {

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
