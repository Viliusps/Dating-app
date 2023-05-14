package com.psap.dating_app.service;

import com.psap.dating_app.model.Question;
import com.psap.dating_app.model.SentQuestion;
import com.psap.dating_app.repository.QuestionRepository;
import com.psap.dating_app.repository.SentQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SentQuestionRepository sentQuestionRepository;

    public QuestionService(QuestionRepository questionRepository, SentQuestionRepository sentQuestionRepository) {
        this.questionRepository = questionRepository;
        this.sentQuestionRepository = sentQuestionRepository;
    }

    public Question getQuestionById(long id) {
        return questionRepository.getQuestion(id);
    }

    public int getRandomNumber() {
        long min = 1L;
        long max = questionRepository.count();
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    public Question getRandomQuestion(long randomNumber) {
        return questionRepository.getQuestion(randomNumber);
    }

    public void saveQuestion(long questionId, long chatId) {
        SentQuestion sentQuestion = new SentQuestion();
        sentQuestion.setDate(new Date());
        sentQuestion.setChatID(chatId);
        sentQuestion.setQuestionID(questionId);
        sentQuestionRepository.save(sentQuestion);
    }
}
