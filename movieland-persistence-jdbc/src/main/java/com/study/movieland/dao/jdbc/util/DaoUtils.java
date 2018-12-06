package com.study.movieland.dao.jdbc.util;

import com.study.movieland.data.MovieRequestParam;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class DaoUtils {

    private static final String HASH_ALGORITM = "SHA-256";

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


    public static String getEncryptedPassword(String password, String salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITM);
            messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't encrypt password", e);
        }
    }
}
