package com.ozay.backend.utility;

import org.joda.time.DateTime;

import java.sql.Timestamp;

/**
 * Created by naofumiezaki on 6/5/16.
 */
public class DateTimeUtility {
    public static Timestamp convertToTimeStamp(DateTime dateTime){
        if(dateTime == null){
            return null;
        }
        Timestamp ts = new Timestamp(dateTime.getMillis());
        return ts;
    }
}
