CREATE TABLE songs
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id                 BIGINT,
    song_id                 VARCHAR(64),
    date_added              TIMESTAMPTZ,
    CONSTRAINT song_pk      PRIMARY KEY (id),
    FOREIGN KEY(user_id)
        REFERENCES users(id)
);

INSERT INTO songs (id, user_id, song_id, date_added) VALUES
(1,1,'561jH07mF1jHuk7KlaeF0s','2023-04-29 01:01:00.000+00'),
(2,1,'7lQ8MOhq6IN2w8EYcFNSUk','2023-04-29 01:02:00.000+00'),
(3,2,'3yfqSUWxFvZELEM4PmlwIR','2023-04-29 01:03:00.000+00');

SELECT SETVAL('public.songs_id_seq', COALESCE(MAX(id), 1) ) FROM public.songs;

CREATE TABLE sent_songs
(
    id                          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    song_id                     BIGINT,
    chat_id                     BIGINT,
    user_id                     BIGINT,
    date_added                  TIMESTAMPTZ,
    CONSTRAINT sent_song_pk     PRIMARY KEY (id),
    FOREIGN KEY(song_id)
        REFERENCES songs(id),
    FOREIGN KEY(chat_id)
        REFERENCES chats(id),
    FOREIGN KEY(user_id)
        REFERENCES users(id)
);

INSERT INTO sent_songs(id,song_id,chat_id,user_id,date_added) VALUES
(1,1,1,1,'2023-04-29 10:55:59.851000 +00:00'),
(2,2,1,1,'2023-04-29 10:56:59.851000 +00:00'),
(3,3,1,2,'2023-04-29 10:57:59.851000 +00:00');

SELECT SETVAL('public.sent_songs_id_seq', COALESCE(MAX(id), 1) ) FROM public.sent_songs;