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
@Table(name = "sent_songs")
public class SentSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "User is mandatory")
    @Column(name = "user_id", nullable = false)
    private long sender;

    @NotNull(message = "Song is mandatory")
    @Column(name = "song_id", nullable = false)
    private long songID;

    @NotNull(message = "Chat is mandatory")
    @Column(name = "chat_id", nullable = false)
    private long chatID;

    @NotNull(message = "Date is mandatory")
    @Column(name = "date_added", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date date;
}
