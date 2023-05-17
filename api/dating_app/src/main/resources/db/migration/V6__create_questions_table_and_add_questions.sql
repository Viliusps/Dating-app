CREATE TABLE questions
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    question VARCHAR(512),
    CONSTRAINT questions_pk    PRIMARY KEY (id)
);

INSERT INTO questions (question) VALUES
('What’s been the best part of your day so far?'),
('What do you do to relax?'),
('What book are you reading right now?'),
('What’s your favorite thing about your hometown?'),
('What’s the last thing you bought online that you really loved?'),
('What would be your perfect weekend?'),
('What’s something (besides your phone) that you take with you everywhere?'),
('What’s the last great show you binged?'),
('What’s your favorite season and why?'),
('What’s your hidden talent?'),
('What’s the last thing that made you laugh out loud?'),
('What was your first car?'),
('Do you have any pets? What are their names?'),
('What were you obsessed with when you were a child?'),
('What’s the most annoying habit a person can have?'),
('Where’s the most beautiful place you’ve ever been?'),
('What’s your favorite song?'),
('Which TV show or movie would you want to come true in real life?'),
('If you could relive a year in your life, what age would you choose?'),
('If you could write a note to your younger self, what would you say in ONLY 3 words?'),
('If you could only eat one meal for the rest of your life, what would it be? '),
('Who is one person in your life that you have a lot of respect for? Why?'),
('If you could wake up tomorrow having gained one quality or ability, what would it be?'),
('Is there something that you’ve dreamed of doing for a long time? Why haven’t you done it?'),
('What is your biggest fear?'),
('What do you feel you’re best at? '),
('What would constitute a perfect day for you?'),
('What is the most embarrassing thing that’s ever happened to you?'),
('What''s a new hobby you''d like to try?'),
('What is one of your favorite memories from childhood?'),
('If you could live anywhere in the world for just one year, where would you live? '),
('If you could have one superpower, what would it be?'),
('Given the choice of anyone in the world, who would you want to have as a dinner guest?'),
('Is intelligence or wisdom more useful?'),
('If you could meet one famous person, who would it be?'),
('Nuclear war has ravaged our city. It’s not longer safe. We’ve just been told that we have 3 days to move before toxicity levels reach deadly. Where are we going?');

SELECT SETVAL('public.questions_id_seq', COALESCE(MAX(id), 1) ) FROM public.questions;

CREATE TABLE sent_questions
(
    id                              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    chat_id                         BIGINT,
    question_id                     BIGINT,
    date_added                      TIMESTAMPTZ,
    CONSTRAINT sent_questions_pk    PRIMARY KEY (id),
    FOREIGN KEY(question_id)
        REFERENCES questions(id),
    FOREIGN KEY(chat_id)
        REFERENCES chats(id)
);

INSERT INTO sent_questions (chat_id, question_id, date_added) VALUES
(1,1,'2023-04-29 10:56:10.851000 +00:00'),
(1,35,'2023-04-29 10:57:10.851000 +00:00'),
(1,12,'2023-04-29 10:58:10.851000 +00:00');

SELECT SETVAL('public.sent_questions_id_seq', COALESCE(MAX(id), 1) ) FROM public.sent_questions;