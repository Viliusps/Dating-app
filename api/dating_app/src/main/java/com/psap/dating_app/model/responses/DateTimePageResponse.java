package com.psap.dating_app.model.responses;

import java.util.Date;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class DateTimePageResponse {
    private String page;
    private List<Date> dates;
    
}
