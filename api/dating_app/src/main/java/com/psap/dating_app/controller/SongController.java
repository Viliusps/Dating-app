package com.psap.dating_app.controller;

import com.psap.dating_app.model.Song;
import com.psap.dating_app.model.requests.SongRecommendationRequest;
import com.psap.dating_app.model.responses.FeatureResponse;
import com.psap.dating_app.model.responses.recommendation_responses.SongIDResponse;
import com.psap.dating_app.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/songs")
public class SongController {
    SongService songService;

    @PostMapping("/recommend")
    public ResponseEntity<SongIDResponse> findSongRecommendation(@RequestBody SongRecommendationRequest request) {
        List<String> userSongs = songService.getSongs(request.getUserID());
        if(userSongs.isEmpty()) return new ResponseEntity<>(new SongIDResponse(), HttpStatus.NOT_FOUND);
        String token = songService.getToken();
        List<FeatureResponse> userFeatures = songService.getFeatures(token,userSongs);

        List<String> otherSongs = songService.getSongs(request.getOtherID());
        List<FeatureResponse> otherFeatures = new ArrayList<>();
        if (!otherSongs.isEmpty()) otherFeatures=songService.getFeatures(token,otherSongs);

        List<FeatureResponse> mergedFeatures = new ArrayList<>();
        mergedFeatures.addAll(userFeatures);
        mergedFeatures.addAll(otherFeatures);

        return new ResponseEntity<>(songService.getRecommendations(token, userSongs, mergedFeatures, request.getUserID(), request.getChatID()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable("id") long id) {
        return new ResponseEntity<>(songService.getSongById(id), HttpStatus.OK);
    }
}
