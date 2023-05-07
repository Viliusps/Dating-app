package com.psap.dating_app.model.responses;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class FeaturesResponse {
    private List<FeatureResponse> audio_features;
}
