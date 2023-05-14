package com.psap.dating_app.repository;

import com.psap.dating_app.model.SentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentQuestionRepository extends JpaRepository<SentQuestion, Long> {
    List<SentQuestion> getSentQuestionsByChatID(long chatId);
}
