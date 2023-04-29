package com.psap.dating_app.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "couple")
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Date is mandatory")
    @Column(name = "date", nullable = false)
    private Date date;

    @NotBlank(message = "weightDiff is mandatory")
    @Column(name = "weight_diff", nullable = false)
    private Integer weightDiff;

    @NotBlank(message = "first is mandatory")
    @Column(name = "first", nullable = false)
    private long first;

    @NotBlank(message = "second is mandatory")
    @Column(name = "second", nullable = false)
    private long second;
}
