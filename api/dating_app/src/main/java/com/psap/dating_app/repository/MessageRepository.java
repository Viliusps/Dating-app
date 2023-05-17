package com.psap.dating_app.repository;

import com.psap.dating_app.model.Message;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    public List<Message> findAllByOrderByIdAsc();

    @Query(
        value = "SELECT * FROM messages m WHERE m.chat=:id",
            nativeQuery = true
        )
    public List<Message> findByChatId(long id);

    @Modifying
    @Query(
        value = "INSERT INTO messages (content, date, sender, chat) VALUES (:message, :date, :sender, :chatId)",
            nativeQuery = true
        )
    @Transactional
    public void notify(long chatId, long sender, String message, Date date);

}