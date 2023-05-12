package com.psap.dating_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psap.dating_app.model.Calendar;


@Repository
public interface DateTimeRepository extends JpaRepository<Calendar, Long> {
    
}
