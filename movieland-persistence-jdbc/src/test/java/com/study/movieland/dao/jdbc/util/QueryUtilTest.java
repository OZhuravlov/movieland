package com.study.movieland.dao.jdbc.util;

import com.study.movieland.entity.MovieRequestParam;
import com.study.movieland.entity.SortDirection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryUtilTest {

    @Test
    public void addOptionalRequestParamsToQueryTest() {
        // no params - no changes
        String sql = "SELECT";
        String actualSql = QueryUtil.addOptionalRequestParamsToQuery(sql, null);
        assertEquals(sql, actualSql);
    }

    @Test
    public void setOrderByTest() {
        String sql = "SELECT";
        String expectedSql;
        String actualSql;

        // rating sorting
        MovieRequestParam params = new MovieRequestParam();
        params.setSortFieldName("rating");
        params.setSortDirection(SortDirection.ASC);
        expectedSql = "SELECT ORDER BY rating ASC";
        actualSql = QueryUtil.setOrderBy(sql, params);
        assertTrue(expectedSql.equalsIgnoreCase(actualSql));

        // price sorting
        MovieRequestParam params2 = new MovieRequestParam();
        params2.setSortFieldName("price");
        params2.setSortDirection(SortDirection.DESC);
        expectedSql = "SELECT ORDER BY price DESC";
        actualSql = QueryUtil.setOrderBy(sql, params2);
        assertTrue(expectedSql.equalsIgnoreCase(actualSql));

        // no sorting - no changes
        MovieRequestParam params3 = new MovieRequestParam();
        actualSql = QueryUtil.setOrderBy(sql, params3);
        assertEquals(sql, actualSql);
    }
}