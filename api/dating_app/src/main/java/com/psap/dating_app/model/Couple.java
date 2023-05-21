package com.psap.dating_app.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psap.dating_app.model.enums.CoupleStatus;

import jakarta.persistence.*;
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
@Table(name = "couples")
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Date is mandatory")
    @Column(name = "date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date date;

    @NotNull(message = "weightDiff is mandatory")
    @Column(name = "weight_diff", nullable = false)
    private Integer weightDiff;

    @NotNull(message = "first is mandatory")
    @Column(name = "first", nullable = false)
    private long first;

    @NotNull(message = "second is mandatory")
    @Column(name = "second", nullable = false)
    private long second;

    @NotBlank(message = "status is mandatory")
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CoupleStatus status;

    @NotNull(message = "chat is mandatory")
    @Column(name = "chat", nullable = false)
    private long chat;
}
