package com.example.malith.test2.basepackage;

/**
 * Created by Malith
 */


import java.util.Date;
import java.sql.Time;


public class Appointment {

     public static final String Id    		= "Id";
    public static final String Title 		= "Title";
    public static final String Date  		= "Date";
    public static final String Time  		= "Time";
    public static final String Description 	= "Description";

    public static final String IsUpdate 	= "isUpdate";
    public static final String IsInViewEdit = "isInViewEdit";
    public static final String IsInSearch 	= "isInSearch";

    private int _intId;

    public void setId(int id)
    {
        this._intId = id;
    }

    public int getId()
    {
        return this._intId;
    }


    private String _strTitle;

    public void setTitle(String title)
    {
        this._strTitle = title;
    }

    public String getTitle()
    {
        return this._strTitle;
    }


    private Date _objDate;

    public void setDate(Date date)
    {
        this._objDate = date;
    }

    public Date getDate()
    {
        if(this._objDate.getYear() < 1900 )
            this._objDate.setYear(this._objDate.getYear()+1900);

        return this._objDate;
    }


    private Time _objTime;

    public void setTime(Time time)
    {
        this._objTime = time;
    }

    public Time getTime()
    {
        return this._objTime;
    }


    private String _strDescription;

    public void setDescription(String description)
    {
        this._strDescription = description;
    }

    public String getDescription()
    {
        return this._strDescription;
    }


    public static String getTableStructure()
    {
        String strTableStructure = "CREATE TABLE Appointment (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Date TEXT,Time TEXT,Description TEXT,UNIQUE(Title,Date))";

        return strTableStructure;
    }

    public void setDate(int year, int month, int dayOfMonth) {

    }


}
