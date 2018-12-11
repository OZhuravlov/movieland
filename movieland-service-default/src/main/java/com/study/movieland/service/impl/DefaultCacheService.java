package com.study.movieland.service.impl;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultCacheService implements CacheService {

    private static final Map<Integer, SoftReference<Movie>> MOVIE_CACHE = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Optional<Movie> getMovie(Integer id) {
        logger.info("Get movie");
        Optional<Movie> optionalMovie = Optional.empty();
        SoftReference<Movie> movieSoftReference = MOVIE_CACHE.get(id);
        if (movieSoftReference != null) {
            optionalMovie = Optional.ofNullable(movieSoftReference.get());
        }
        return optionalMovie;
    }

    @Override
    public void putMovie(Movie movie) {
        MOVIE_CACHE.put(movie.getId(), new SoftReference<>(movie));
    }

    @Override
    public void removeMovie(int id) {
        MOVIE_CACHE.remove(id);
    }

    @Override
    public boolean existsMovie(int id) {
        return MOVIE_CACHE.containsKey(id);
    }


}
