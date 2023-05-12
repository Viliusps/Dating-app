package com.psap.dating_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.psap.dating_app.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Modifying
    @Query(
        value = "DELETE FROM events e WHERE e.user_id=:userId",
        nativeQuery = true
    )
    @Transactional
    public void deleteAllPreviousEvents(long userId);


    @Query(
        value = "SELECT * FROM events e WHERE e.user_id=:userId",
        nativeQuery = true
    )
    public List<Event> getByUserId(long userId);
}