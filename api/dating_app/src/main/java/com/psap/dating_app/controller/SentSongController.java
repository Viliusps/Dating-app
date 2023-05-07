package com.psap.dating_app.controller;

import com.psap.dating_app.model.SentSong;
import com.psap.dating_app.service.SentSongService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/sent_songs")
public class SentSongController {
    SentSongService sentSongService;

    @GetMapping("/byChat/{id}")
    public ResponseEntity<List<SentSong>> getSentSongsByChatId(@PathVariable("id") long id) {
        return new ResponseEntity<>(sentSongService.getSongsByChatId(id), HttpStatus.OK);
    }
}
