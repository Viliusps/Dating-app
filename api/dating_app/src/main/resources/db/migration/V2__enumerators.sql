CREATE TYPE couple_status AS ENUM 
('LIKES', 'DISLIKES', 'UNDECIDED');

CREATE TYPE roles AS ENUM
('ADMIN', 'USER');

CREATE TYPE genders AS ENUM 
('MALE', 'FEMALE', 'OTHER');

CREATE TYPE search_genders AS ENUM
('MALE', 'FEMALE', 'OTHER', 'ANY');

CREATE TYPE purposes AS ENUM
('SHORT', 'LONG');