package org.qamation.utils;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    public static String changeDate(String currentDate, String currentFormat, int shift, int calendarField, String newFormat) {
        SimpleDateFormat curForm = new SimpleDateFormat(currentFormat);
        SimpleDateFormat newForm = new SimpleDateFormat(newFormat);
        ParsePosition pos = new ParsePosition(0);
        FieldPosition f_pos = new FieldPosition(0);
        Calendar c = Calendar.getInstance();
        c.setTime(curForm.parse(currentDate,pos));
        c.add(calendarField,shift);
        StringBuffer buff = new StringBuffer();
        buff = newForm.format(c.getTime(),buff,f_pos);
        return buff.toString();
    }
}
