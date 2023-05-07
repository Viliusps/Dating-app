package com.psap.dating_app.model.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class FeatureResponse {
    private Float danceability;
    private Float energy;
    private Long key;
    private Float loudness;
    private Long mode;
    private Float speechiness;
    private Float acousticness;
    private Float instrumentalness;
    private Float liveness;
    private Float valence;
    private Float tempo;
    private String type;
    private String id;
    private String uri;
    private String track_href;
    private String analysis_url;
    private Long duration_ms;
    private Long time_signature;
}
