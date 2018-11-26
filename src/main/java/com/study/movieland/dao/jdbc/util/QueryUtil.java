package com.study.movieland.dao.jdbc.util;

import com.study.movieland.entity.MovieRequestParam;
import com.study.movieland.entity.SortDirection;

public class QueryUtil {

    private final static String RATING_FIELD_NAME = "rating";
    private final static String PRICE_FIELD_NAME = "price";

    public static String addOptionalRequestParamsToQuery(String sql, MovieRequestParam params){
        if (params == null) {
            return sql;
        }
        sql = setOrderBy(sql, params);
        return sql;
    }

    protected static String setOrderBy(String sql, MovieRequestParam params){
        String sortField = null;
        SortDirection sortDirection = null;
        if (params.getRatingSorting() != null){
            sortField = RATING_FIELD_NAME;
            sortDirection = params.getRatingSorting();
        } else if(params.getPriceSorting() != null){
            sortField = PRICE_FIELD_NAME;
            sortDirection = params.getPriceSorting();
        }
        if(sortField != null){
            sql += " ORDER BY " + sortField + " " + sortDirection;
        }
        return sql;
    }
}
