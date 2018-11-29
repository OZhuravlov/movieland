package com.study.movieland.web.converter;

import com.study.movieland.entity.SortDirection;
import com.study.movieland.exception.BadRequestParamException;
import org.junit.Test;

import static org.junit.Assert.*;

public class SortDirectionConverterTest {

    @Test
    public void setAsTextTest() {
        SortDirectionConverter converter = new SortDirectionConverter();

        String param = "NONE";
        converter.setAsText(param);
        SortDirection sortDirection = (SortDirection)converter.getValue();
        assertNull(sortDirection);

        String param2 = "asc";
        converter.setAsText(param2);
        SortDirection sortDirection2 = (SortDirection)converter.getValue();
        assertEquals(SortDirection.ASC, sortDirection2);

        String param3 = "desc";
        converter.setAsText(param3);
        SortDirection sortDirection3 = (SortDirection)converter.getValue();
        assertEquals(SortDirection.DESC, sortDirection3);

    }

    @Test(expected = BadRequestParamException.class)
    public void setAsTextExceptionTest() {
        SortDirectionConverter converter = new SortDirectionConverter();
        String param = "bad";
        converter.setAsText(param);
    }
}