package com.psap.dating_app.service;

import com.psap.dating_app.model.SentSong;
import com.psap.dating_app.repository.SentSongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SentSongService {
    SentSongRepository sentSongRepository;

    public List<SentSong> getSongsByChatId(long id) {
        return sentSongRepository.findAllByChatIDOrderByDate(id);
    }
}
