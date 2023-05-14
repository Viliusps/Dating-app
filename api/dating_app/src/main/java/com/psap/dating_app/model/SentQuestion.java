package com.psap.dating_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sent_questions")
public class SentQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Chat is mandatory")
    @Column(name = "chat_id", nullable = false)
    private long chatID;

    @NotNull(message = "Question is mandatory")
    @Column(name = "question_id", nullable = false)
    private long questionID;

    @NotNull(message = "Date is mandatory")
    @Column(name = "date_added", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date date;
}
