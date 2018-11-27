package com.study.movieland.dao.cache;

import com.google.common.collect.ImmutableMap;
import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.exception.NoDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CacheGenreDao implements GenreDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GenreDao genreDao;
    volatile private Map<Integer, Genre> genres;

    @PostConstruct
    @Scheduled(fixedDelayString = "${scheduler.fixedDelayInMilliseconds}",
            initialDelayString = "${scheduler.initDelayInMilliseconds}")
    public void init() {
        logger.info("Refreshing cache");
        Collection<Genre> genreList = genreDao.getAll();
        logger.info("Got {} genres from dao", genreList.size());
        Map<Integer, Genre> genreMap = new HashMap<>();
        genreList.forEach(genre -> genreMap.put(genre.getId(), genre));
        genres = ImmutableMap.copyOf(genreMap);
    }

    @Override
    public Collection<Genre> getAll() {
        logger.info("get All Genres from cache");
        Collection<Genre> values = genres.values();
        logger.debug("getAll: return List of {} Genre instances from cache", genres.values().size());
        return values;
    }

    @Override
    public Genre getById(int id) throws NoDataFoundException {
        logger.info("getting genre by id {} from cache", id);
        Genre genre = genres.get(id);
        if (genre == null) {
            String warnMessage = "Not such genre with id " + id + " in cache";
            logger.warn(warnMessage);
            throw new NoDataFoundException(warnMessage);
        }
        return genre;
    }

    @Resource(name = "jdbcGenreDao")
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
}
