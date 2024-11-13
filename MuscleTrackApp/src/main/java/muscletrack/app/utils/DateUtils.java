package muscletrack.app.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    TimeZone timeZone;
    DateFormat ymd;
    DateFormat ISO;
    Calendar cal;

    public DateUtils(){
        timeZone = TimeZone.getTimeZone("UTC");
        ymd = new SimpleDateFormat("yyyy-MM-dd");
        ISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        ymd.setTimeZone(timeZone);
        ISO.setTimeZone(timeZone);

        cal = Calendar.getInstance();
    }

    public String convertIsoToYmd(String date){
        try {
            return ymd.format(ISO.parse(date));
        } catch (ParseException e) {
            System.out.println("Could not convert from ISO to yyyy-MM-dd");
            throw new RuntimeException(e);
        }
    }

    public String convertYmdToIso(String date){
        try {
            return ISO.format(ymd.parse(date));
        } catch (ParseException e) {
            System.out.println("Could not convert from yyyy-MM-dd to ISO");
            throw new RuntimeException(e);
        }
    }

    public Date convertIsoToDate(String data){
        int index = data.indexOf(".");
        if (index > -1) {
            data = data.substring(0, index) + "Z";
        }
        try {
            return ISO.parse(data);
        } catch (Exception e) {
            return null;
        }
    }

    public Date convertYmdToDate(String date){
        try {
            return ymd.parse(date);
        } catch (ParseException e) {
            System.out.println("Could not convert from yyyy-MM-dd to DATE");
            throw new RuntimeException(e);
        }
    }

    public int getMonth(){
        return cal.get(Calendar.MONTH) + 1;
    }

    public int getYear(){
        return cal.get(Calendar.YEAR);
    }

    public int getToday(){
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getWeekDay(){
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public int getMonthWeek(){
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    public long betweenDates(Date firstDate, Date secondDate) throws IOException
    {
        return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    }
}
