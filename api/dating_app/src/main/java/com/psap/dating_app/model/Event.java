package com.psap.dating_app.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psap.dating_app.model.enums.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Start date is mandatory")
    @Column(name = "start_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date startDate;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date endDate;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "lattitude")
    private String lattitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;
}
