package com.study.movieland.dao.cache;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.exception.NoDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository("cacheGenreDao")
public class CacheGenreDao implements GenreDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    private GenreDao genreDao;
    private Map<Integer, Genre> genres;

    @PostConstruct
    @Scheduled(fixedDelayString = "${scheduler.fixedDelayInMilliseconds}",
            initialDelayString = "${scheduler.initDelayInMilliseconds}")
    public void init() {
        logger.info("Refreshing cache");
        readLock.lock();

        List<Genre> genreList = genreDao.getAll();
        logger.info("Got {} genres from dao", genreList.size());
        Map<Integer, Genre> genreMap = new HashMap<>();
        genreList.forEach(genre -> genreMap.put(genre.getId(), genre));

        readLock.unlock();
        if(!writeLock.tryLock()){
            logger.warn("Another process locked cache for refreshing. Waiting for lock release");
            writeLock.lock();
        }
        try {
            genres = genreMap;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<Genre> getAll() {
        logger.info("get All Genres from cache");
        logger.debug("getAll: return List of {} Genre instances from cache", genres);
        readLock.lock();
        try {
            if (genres == null) {
                logger.warn("genre cache is empty");
                return null;
            }
            List<Genre> genreList = new ArrayList<>(genres.values());
            return genreList;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Genre getById(int id) throws NoDataFoundException {
        logger.info("getting genre by id {} from cache", id);
        readLock.lock();
        try {
            Genre genre = genres.get(id);
            if (genre == null) {
                String warnMessage = "Not such genre with id " + id + " in cache";
                logger.warn(warnMessage);
                throw new NoDataFoundException(warnMessage);
            }
            return genre;
        } finally {
            readLock.unlock();
        }
    }

    @Resource(name = "jdbcGenreDao")
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
}
