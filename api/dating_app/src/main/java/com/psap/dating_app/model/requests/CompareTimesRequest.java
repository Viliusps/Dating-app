package com.psap.dating_app.model.requests;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompareTimesRequest {
    private List<Date> selectedTimes;
    private List<Date> recommendedTimes;
}
