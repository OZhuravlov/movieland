CREATE SEQUENCE seq_users;
ALTER TABLE users ALTER COLUMN id SET DEFAULT NEXTVAL('seq_users');
ALTER SEQUENCE seq_users OWNED BY users.id;
SELECT setval('seq_users', next_id) FROM (SELECT MAX(id)+1 next_id FROM users) AS t;

CREATE SEQUENCE seq_genres;
ALTER TABLE genres ALTER COLUMN id SET DEFAULT NEXTVAL('seq_genres');
ALTER SEQUENCE seq_genres OWNED BY genres.id;
SELECT setval('seq_genres', next_id) FROM (SELECT MAX(id)+1 next_id FROM genres) AS t;

CREATE SEQUENCE seq_movie_genres;
ALTER TABLE movie_genres ALTER COLUMN id SET DEFAULT NEXTVAL('seq_movie_genres');
ALTER SEQUENCE seq_movie_genres OWNED BY movie_genres.id;
SELECT setval('seq_movie_genres', next_id) FROM (SELECT MAX(id)+1 next_id FROM movie_genres) AS t;

CREATE SEQUENCE seq_movies;
ALTER TABLE movies ALTER COLUMN id SET DEFAULT NEXTVAL('seq_movies');
ALTER SEQUENCE seq_movies OWNED BY movies.id;
SELECT setval('seq_movies', next_id) FROM (SELECT MAX(id)+1 next_id FROM movies) AS t;

CREATE SEQUENCE seq_reviews;
ALTER TABLE reviews ALTER COLUMN id SET DEFAULT NEXTVAL('seq_reviews');
ALTER SEQUENCE seq_reviews OWNED BY reviews.id;
SELECT setval('seq_reviews', next_id) FROM (SELECT MAX(id)+1 next_id FROM reviews) AS t;
