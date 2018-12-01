package com.study.movieland.dao.jdbc.util;

import com.study.movieland.data.MovieRequestParam;

public class QueryUtil {

    public static String addOptionalRequestParamsToQuery(String sql, MovieRequestParam params) {
        if (params == null) {
            return sql;
        }
        sql = setOrderBy(sql, params);
        return sql;
    }

    static String setOrderBy(String sql, MovieRequestParam params) {
        if (params.getSortFieldName() != null) {
            sql += " ORDER BY " + params.getSortFieldName() + " " + params.getSortDirection();
        }
        return sql;
    }
}
