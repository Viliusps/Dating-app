package com.psap.dating_app.service;

import com.psap.dating_app.model.SentQuestion;
import com.psap.dating_app.repository.SentQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentQuestionService {
    private final SentQuestionRepository sentQuestionRepository;

    public SentQuestionService(SentQuestionRepository sentQuestionRepository) {
        this.sentQuestionRepository = sentQuestionRepository;
    }

    public List<SentQuestion> getSentQuestionByChat(long chatId) {
        return sentQuestionRepository.getSentQuestionsByChatID(chatId);
    }
}
