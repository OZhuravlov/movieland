package com.study.movieland.service.impl;

import com.study.movieland.data.MovieRequestParam;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.CurrencyService;
import com.study.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Primary
public class CacheMovieService implements MovieService {

    private final Map<Integer, SoftReference<Movie>> MOVIE_CACHE = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;
    private CurrencyService currencyService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public List<Movie> getAll(MovieRequestParam movieRequestParam) {
        return movieService.getAll(movieRequestParam);
    }

    @Override
    public List<Movie> getRandom() {
        return movieService.getRandom();
    }

    @Override
    public List<Movie> getByGenre(int genreId, MovieRequestParam movieRequestParam) {
        return movieService.getByGenre(genreId, movieRequestParam);
    }

    @Override
    public Movie getById(int id, MovieRequestParam movieRequestParam) {
        logger.info("Get movie");
        Optional<Movie> optionalMovie = Optional.empty();
        SoftReference<Movie> movieSoftReference = MOVIE_CACHE.get(id);
        Movie movie;
        if (movieSoftReference != null) {
            optionalMovie = Optional.ofNullable(movieSoftReference.get());
        }
        if (optionalMovie.isPresent()) {
            logger.info("Get movie id {} from cache", id);
            movie = optionalMovie.get();
            currencyService.enrichMoviePriceInCurrency(movie, movieRequestParam.getCurrency());
            return movie;
        }
        movie = movieService.getById(id, movieRequestParam);
        logger.info("Put movie id {} into cache", id);
        MOVIE_CACHE.put(id, new SoftReference<>(movie));
        return movie;
    }

    @Override
    public void edit(Movie movie) {
        movieService.edit(movie);
        int id = movie.getId();
        logger.info("remove movie id {} from cache", id);
        MOVIE_CACHE.remove(id);
    }

    @Override
    public void add(Movie movie) {
        movieService.add(movie);
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
}
