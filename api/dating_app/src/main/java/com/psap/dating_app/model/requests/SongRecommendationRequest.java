package com.psap.dating_app.model.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class SongRecommendationRequest {
    private Long userID;
    private Long otherID;
    private Long chatID;
}
