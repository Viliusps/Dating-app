package com.psap.dating_app.repository;

import com.psap.dating_app.model.Chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    public List<Chat> findAllByOrderByIdAsc();
}