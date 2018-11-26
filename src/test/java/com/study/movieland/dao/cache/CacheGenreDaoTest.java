package com.study.movieland.dao.cache;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.exception.NoDataFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CacheGenreDaoTest {

    CacheGenreDao genreDao;
    StubGenreDao stubGenreDao;

    @Before
    public void init() {
        genreDao = new CacheGenreDao();
        stubGenreDao = new StubGenreDao();
        genreDao.setGenreDao(stubGenreDao);
    }

    @Test
    public void initAndGetAllTest() {
        genreDao.init();
        Collection<Genre> genres = genreDao.getAll();
        assertEquals(2, genres.size());
        assertThat(genres, is(stubGenreDao.getAll()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllImmutableTest() {
        genreDao.init();
        Collection<Genre> genres = genreDao.getAll();
        genres.add(new Genre(3, "Genre 3"));
    }

    @Test
    public void getByIdTest() {
        genreDao.init();
        Genre genre = genreDao.getById(2);
        assertThat(genre, is(stubGenreDao.getAll().get(1)));
    }

    @Test(expected = NoDataFoundException.class)
    public void getByIdNoDataFoundExceptionTest() {
        genreDao.init();
        genreDao.getById(100);
    }

    private static class StubGenreDao implements GenreDao {
        List<Genre> genres = new ArrayList<Genre>() {{
            add(new Genre(1, "genre 1"));
            add(new Genre(2, "genre 2"));
        }};

        @Override
        public List<Genre> getAll() {
            return genres;
        }

        @Override
        public Genre getById(int id) {
            return genres.get(id);
        }
    }
}