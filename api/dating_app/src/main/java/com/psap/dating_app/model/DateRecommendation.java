package com.psap.dating_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "date_place_recommendations")
public class DateRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "User is mandatory")
    @Column(name = "user_id", nullable = false)
    private long userID;

    @NotNull(message = "Match is mandatory")
    @Column(name = "match_id", nullable = false)
    private long matchID;

    @NotNull(message = "Date is mandatory")
    @Column(name = "date_added", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date date;

    @Column(name = "lattitude")
    private String lattitude;

    @Column(name = "longitude")
    private String longitude;

}
