package com.psap.dating_app.repository;

import com.psap.dating_app.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(
            value = "SELECT * FROM questions ORDER BY id LIMIT 1 OFFSET :index-1",
            nativeQuery = true
    )
    Question getQuestion(long index);
}
