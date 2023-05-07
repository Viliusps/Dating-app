package com.psap.dating_app.model.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class RecommendationRequest {
    private Long limit;
    private String seed_tracks;
    private Float target_acousticness = (float) 0;
    private Float target_danceability = (float) 0;
    private Long target_duration_ms = 0L;
    private Float target_energy = (float) 0;
    private Float target_instrumentalness = (float) 0;
    private Long target_key = 0L;
    private Float target_liveness = (float) 0;
    private Float target_loudness = (float) 0;
    private Long target_mode = 0L;
    private Float target_speechiness = (float) 0;
    private Float target_tempo = (float) 0;
    private Long target_time_signature = 0L;
    private Float target_valence = (float) 0;
}
