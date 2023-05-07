package com.psap.dating_app.repository;

import com.psap.dating_app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query(
            value = "SELECT s.song_id FROM songs s WHERE user_id = :userID ORDER BY date_added DESC LIMIT 100",
            nativeQuery = true
    )
    List<String> getFirst100ByUserID(long userID);

    Song getSongById(long id);
}
