package com.study.movieland.dao.jdbc.util;

import com.study.movieland.entity.MovieRequestParam;
import com.study.movieland.entity.SortDirection;

public class QueryUtil {

    public static String addOptionalRequestParamsToQuery(String sql, MovieRequestParam params) {
        if (params == null) {
            return sql;
        }
        sql = setOrderBy(sql, params);
        return sql;
    }

    protected static String setOrderBy(String sql, MovieRequestParam params) {
        if (params.getSortFieldName() != null) {
            sql += " ORDER BY " + params.getSortFieldName() + " " + params.getSortDirection();
        }
        return sql;
    }
}
