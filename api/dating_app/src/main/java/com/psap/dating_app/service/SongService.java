package com.psap.dating_app.service;
import java.util.*;

import com.psap.dating_app.model.SentSong;
import com.psap.dating_app.model.Song;
import com.psap.dating_app.model.requests.RecommendationRequest;
import com.psap.dating_app.model.responses.FeatureResponse;
import com.psap.dating_app.model.responses.FeaturesResponse;
import com.psap.dating_app.model.responses.TokenResponse;
import com.psap.dating_app.model.responses.recommendation_responses.RecommendationTracksResponse;
import com.psap.dating_app.model.responses.recommendation_responses.RecommendationsResponse;
import com.psap.dating_app.model.responses.recommendation_responses.SongIDResponse;
import com.psap.dating_app.repository.SentSongRepository;
import com.psap.dating_app.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class SongService {
    SongRepository songRepository;
    SentSongRepository sentSongRepository;

    public List<String> getAllSongsByUser(long id) {
        return songRepository.getFirst100ByUserID(id);
    }

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("80ba9b6b96594ee2a8f6c47cd546c67f","646fba8faf074f5caab3a35d134e2a28");
        String uri = ("https://accounts.spotify.com/api/token");
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type","client_credentials");
        HttpEntity<MultiValueMap<String, String>> httpEntity=new HttpEntity<>(form,headers);
        RestTemplate restTemplate = new RestTemplate();
        TokenResponse response = restTemplate.postForObject(uri,httpEntity,TokenResponse.class);

        return response.getAccess_token();
    }

    public List<FeatureResponse> getFeatures(String token, List<String> songs) {
        StringBuilder idsRequest= new StringBuilder();
        for (int i=0; i<songs.size(); i++) {
            if(i+1!=songs.size())
                idsRequest.append(songs.get(i)).append(",");
            else
                idsRequest.append(songs.get(i));
        }
        String url = "https://api.spotify.com/v1/audio-features?ids="+idsRequest;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        return new RestTemplate().exchange(url, HttpMethod.GET, request, FeaturesResponse.class).getBody().getAudio_features();
    }

    public SongIDResponse getRecommendation(String token, List<String> songs, List<FeatureResponse> features, Long userID, Long chatID) {
        RecommendationRequest recommendationRequest = getFeatureAverage(features);
        List<String> seedTracks = songs.stream().limit(5).toList();
        StringBuilder refactoredSeedTracks = new StringBuilder();
        for (int i=0; i<seedTracks.size(); i++) {
            if(i+1==seedTracks.size())
                refactoredSeedTracks.append(seedTracks.get(i));
            else
                refactoredSeedTracks.append(seedTracks.get(i)).append(",");
        }
        recommendationRequest.setLimit(100L);
        recommendationRequest.setSeed_tracks(String.valueOf(refactoredSeedTracks));

        String url = "https://api.spotify.com/v1/recommendations?seed_tracks={seed_tracks}&limit={limit}&target_acousticness={target_acousticness}" +
                "&target_danceability={target_danceability}&target_energy={target_energy}" +
                "&target_instrumentalness={target_instrumentalness}&target_key={target_key}&target_liveness={target_liveness}" +
                "&target_loudness={target_loudness}&target_mode={target_mode}&target_speechiness={target_speechiness}" +
                "&target_tempo={target_tempo}&target_time_signature={target_time_signature}&target_valence={target_valence}";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity request = new HttpEntity(headers);
        Map<String,String> params = new HashMap<>();
        params.put("seed_tracks","7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");
        params.put("limit","100");
        params.put("target_acousticness",recommendationRequest.getTarget_acousticness().toString());
        params.put("target_danceability",recommendationRequest.getTarget_danceability().toString());
        params.put("target_duration_ms",recommendationRequest.getTarget_duration_ms().toString());
        params.put("target_energy",recommendationRequest.getTarget_energy().toString());
        params.put("target_instrumentalness",recommendationRequest.getTarget_instrumentalness().toString());
        params.put("target_key",recommendationRequest.getTarget_key().toString());
        params.put("target_liveness",recommendationRequest.getTarget_liveness().toString());
        params.put("target_loudness",recommendationRequest.getTarget_loudness().toString());
        params.put("target_mode",recommendationRequest.getTarget_mode().toString());
        params.put("target_speechiness",recommendationRequest.getTarget_speechiness().toString());
        params.put("target_tempo",recommendationRequest.getTarget_tempo().toString());
        params.put("target_time_signature",recommendationRequest.getTarget_time_signature().toString());
        params.put("target_valence",recommendationRequest.getTarget_valence().toString());

        List<RecommendationTracksResponse> tracks = new RestTemplate().exchange(url, HttpMethod.GET, request, RecommendationsResponse.class, params).getBody().getTracks();
        boolean random = true;
        String chosenId = "";
        for (RecommendationTracksResponse track : tracks) {
            String id = track.getId();
            if (!songs.contains(id)) {
                random = false;
                chosenId = id;
                break;
            }
        }
        if (random) chosenId = tracks.get(new Random().nextInt(100)).getId();

        Song newSong = new Song();
        newSong.setSongID(chosenId);
        newSong.setUserID(userID);
        newSong.setDate(new Date());
        Song savedSong = songRepository.save(newSong);

        SentSong newSentSong = new SentSong();
        newSentSong.setSongID(savedSong.getId());
        newSentSong.setSender(userID);
        newSentSong.setDate(new Date());
        newSentSong.setChatID(chatID);
        sentSongRepository.save(newSentSong);

        SongIDResponse songIDResponse = new SongIDResponse();
        songIDResponse.setSongID(chosenId);
        return songIDResponse;
    }

    public RecommendationRequest getFeatureAverage(List<FeatureResponse> features) {
        RecommendationRequest recommendationRequest = new RecommendationRequest();
        for (FeatureResponse feature : features) {
            recommendationRequest.setTarget_acousticness(recommendationRequest.getTarget_acousticness() + feature.getAcousticness());
            recommendationRequest.setTarget_danceability(recommendationRequest.getTarget_danceability() + feature.getDanceability());
            recommendationRequest.setTarget_duration_ms(recommendationRequest.getTarget_duration_ms() + feature.getDuration_ms());
            recommendationRequest.setTarget_energy(recommendationRequest.getTarget_energy() + feature.getEnergy());
            recommendationRequest.setTarget_instrumentalness(recommendationRequest.getTarget_instrumentalness() + feature.getInstrumentalness());
            recommendationRequest.setTarget_key(recommendationRequest.getTarget_key() + feature.getKey());
            recommendationRequest.setTarget_liveness(recommendationRequest.getTarget_liveness() + feature.getLiveness());
            recommendationRequest.setTarget_loudness(recommendationRequest.getTarget_loudness() + feature.getLoudness());
            recommendationRequest.setTarget_mode(recommendationRequest.getTarget_mode() + feature.getMode());
            recommendationRequest.setTarget_speechiness(recommendationRequest.getTarget_speechiness() + feature.getSpeechiness());
            recommendationRequest.setTarget_tempo(recommendationRequest.getTarget_tempo() + feature.getTempo());
            recommendationRequest.setTarget_time_signature(recommendationRequest.getTarget_time_signature() + feature.getTime_signature());
            recommendationRequest.setTarget_valence(recommendationRequest.getTarget_valence() + feature.getValence());
        }

        recommendationRequest.setTarget_acousticness(recommendationRequest.getTarget_acousticness()/features.size());
        recommendationRequest.setTarget_danceability(recommendationRequest.getTarget_danceability()/features.size());
        recommendationRequest.setTarget_duration_ms(recommendationRequest.getTarget_duration_ms()/features.size());
        recommendationRequest.setTarget_energy(recommendationRequest.getTarget_energy()/features.size());
        recommendationRequest.setTarget_instrumentalness(recommendationRequest.getTarget_instrumentalness()/features.size());
        recommendationRequest.setTarget_key(recommendationRequest.getTarget_key()/features.size());
        recommendationRequest.setTarget_liveness(recommendationRequest.getTarget_liveness()/features.size());
        recommendationRequest.setTarget_loudness(recommendationRequest.getTarget_loudness()/features.size());
        recommendationRequest.setTarget_mode(recommendationRequest.getTarget_mode()/features.size());
        recommendationRequest.setTarget_speechiness(recommendationRequest.getTarget_speechiness()/features.size());
        recommendationRequest.setTarget_tempo(recommendationRequest.getTarget_tempo()/features.size());
        recommendationRequest.setTarget_time_signature(recommendationRequest.getTarget_time_signature()/features.size());
        recommendationRequest.setTarget_valence(recommendationRequest.getTarget_valence()/features.size());

        return recommendationRequest;
    }

    public Song getSongById(long id) {
        return songRepository.getSongById(id);
    }
}
