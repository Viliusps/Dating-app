package com.psap.dating_app.model.responses.recommendation_responses;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class RecommendationsResponse {
    private List<RecommendationTracksResponse> tracks;
}
