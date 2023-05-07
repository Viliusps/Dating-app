package com.psap.dating_app.repository;

import com.psap.dating_app.model.SentSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SentSongRepository extends JpaRepository<SentSong, Long> {
    List<SentSong> findAllByChatIDOrderByDate(long chatID);
}
