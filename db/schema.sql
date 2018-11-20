DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS movie_genres;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS genres;

CREATE TABLE users
( id         INT           PRIMARY KEY
, username   VARCHAR(100)  NOT NULL
, password   VARCHAR(250)  NOT NULL
, sole       VARCHAR(100)  NOT NULL
, name       VARCHAR(100)  NOT NULL
, surname    VARCHAR(100)
, email      VARCHAR(100)  NOT NULL
, role       VARCHAR(20)   NOT NULL
);

CREATE TABLE genres
( id         INT           PRIMARY KEY
, name       VARCHAR(100)  NOT NULL UNIQUE
);

CREATE TABLE movies
( id              INT           PRIMARY KEY
, name_native     VARCHAR(500)  NOT NULL
, name_russian    VARCHAR(500)  NOT NULL
, year_of_release INT           NOT NULL
, country         VARCHAR(500)
, description     TEXT
, rating          NUMERIC(5,2)
, price           NUMERIC(15,2) NOT NULL
, picture_path    VARCHAR(4000)
);

CREATE TABLE movie_genres
( id         INT           PRIMARY KEY
, movie_id   INT           NOT NULL REFERENCES movies(id)
, genre_id   INT           NOT NULL REFERENCES genres(id)
);

CREATE TABLE reviews
( id              INT          PRIMARY KEY
, movie_id        INT          NOT NULL REFERENCES movies(id)
, user_id         INT          NOT NULL REFERENCES users(id)
, review_text     TEXT         NOT NULL
);
