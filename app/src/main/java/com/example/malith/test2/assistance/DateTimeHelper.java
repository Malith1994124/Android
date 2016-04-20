package com.example.malith.test2.assistance;

/**
 * Created by Malith
 */

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

    public static String dateToString(Date date)
    {
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");

        if(date.getYear() > 1900)
            date = new Date(date.getYear() - 1900,date.getMonth(),date.getDate());

        return dfDate.format(date);
    }

    public static Date stringToDate(String date) throws ParseException
    {
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");

        return (Date)dfDate.parse(date);
    }

    public static String timeToString(Time time)
    {
        SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");

        return dfTime.format(time);
    }

    public static Time stringToTime(String time)throws ParseException
    {
        SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");

        return new Time(dfTime.parse(time).getTime());
    }

    public static boolean isSelectedDateLowerThanCurrentDate(Date date)
    {
        Date objCurrentDate = new Date();

        if(date.getYear() > (objCurrentDate.getYear()+1900))
            return false;

        else if (date.getYear() < (objCurrentDate.getYear()+1900))
            return true;

        else if(date.getMonth() < objCurrentDate.getMonth())
            return true;

        else if(date.getDate() < objCurrentDate.getDate())
            return true;

        else
            return false;
    }

    public static boolean isSelectedDateEqualsToCurrentDate(Date date)
    {
        Date objCurrentDate = new Date();

        if((date.getYear() == (objCurrentDate.getYear()+1900)) && (date.getMonth() == objCurrentDate.getMonth()) && (date.getDate() == objCurrentDate.getDate()))
            return true;

        else
            return false;
    }

    public static boolean isSelectedTimeLowerThanCurrentTime(Time time)
    {
        Date objDate = new Date();

        if(objDate.getHours() < time.getHours())
            return false;
        else if(objDate.getHours()>time.getHours())
            return true;
        else if(time.getMinutes() < objDate.getMinutes())
            return true;
        else
            return false;
    }

}
