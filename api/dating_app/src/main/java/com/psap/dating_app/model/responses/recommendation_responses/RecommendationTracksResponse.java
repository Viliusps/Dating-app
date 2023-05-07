package com.psap.dating_app.model.responses.recommendation_responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class RecommendationTracksResponse {
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private String name;
    private Long popularity;
}
