package com.psap.dating_app.repository;

import com.psap.dating_app.model.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    public List<Message> findAllByOrderByIdAsc();

        @Query(
        value = "SELECT * FROM messages m WHERE m.chat=:id",
            nativeQuery = true
        )
    public List<Message> findByChatId(long id);
}