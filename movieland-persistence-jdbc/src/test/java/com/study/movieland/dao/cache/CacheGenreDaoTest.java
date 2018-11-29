package com.study.movieland.dao.cache;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        List<Genre> genres = genreDao.getAll();
        assertEquals(2, genres.size());
        assertThat(genres, is(stubGenreDao.getAll()));
    }

    @Test
    public void getByIdTest() {
        genreDao.init();
        Genre genre = genreDao.getById(2);
        assertThat(genre, is(stubGenreDao.getAll().get(1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByIdIllegalArgumentExceptionTest() {
        genreDao.init();
        genreDao.getById(100);
    }

    private static class StubGenreDao implements GenreDao {
        List<Genre> genres = new ArrayList<Genre>() {{
            add(Genre.newBuilder()
                    .setId(1)
                    .setName("genre 1")
                    .build());
            add(Genre.newBuilder()
                    .setId(2)
                    .setName("genre 2")
                    .build());
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