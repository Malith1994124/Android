package com.example.malith.test2.datamanager;

/**
 * Created by Malith
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.ParseException;
import com.example.malith.test2.basepackage.Appointment;
import com.example.malith.test2.basepackage.Appointments;
import com.example.malith.test2.assistance.DateTimeHelper;



public class DataBaseHelper extends SQLiteOpenHelper {


    String[] columns = new String[]{Appointment.Id,Appointment.Title,Appointment.Date,Appointment.Time,Appointment.Description};

    public DataBaseHelper(Context context)
    {
        super(context,"AppointmentManagementSystem",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDb)
    {
        sqliteDb.execSQL(Appointment.getTableStructure());
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqlDb,int args1,int args2)
    {
    }

    private Appointment fillAppointment(Cursor cursor) throws ParseException
    {
        Appointment objAppointment = new Appointment();

        while(cursor.moveToNext())
        {
            objAppointment.setId(cursor.getInt(cursor.getColumnIndex(Appointment.Id)));
            objAppointment.setTitle(cursor.getString(cursor.getColumnIndex(Appointment.Title)));
            objAppointment.setTime(DateTimeHelper.stringToTime(cursor.getString(cursor.getColumnIndex(Appointment.Time))));
            objAppointment.setDate(DateTimeHelper.stringToDate(cursor.getString(cursor.getColumnIndex(Appointment.Date))));
            objAppointment.setDescription(cursor.getString(cursor.getColumnIndex(Appointment.Description)));
        }
        return objAppointment;
    }


    private Appointments fillAppointments(Cursor cursor) throws ParseException
    {
        Appointments objAppointments = new Appointments();

        while(cursor.moveToNext())
        {
            Appointment objAppointment = new Appointment();

            objAppointment.setId(cursor.getInt(cursor.getColumnIndex(Appointment.Id)));
            objAppointment.setTitle(cursor.getString(cursor.getColumnIndex(Appointment.Title)));
            objAppointment.setTime(DateTimeHelper.stringToTime(cursor.getString(cursor.getColumnIndex(Appointment.Time))));
            objAppointment.setDate(DateTimeHelper.stringToDate(cursor.getString(cursor.getColumnIndex(Appointment.Date))));
            objAppointment.setDescription(cursor.getString(cursor.getColumnIndex(Appointment.Description)));

            objAppointments.add(objAppointment);
        }
        return objAppointments;
    }


    public void appointment_Insert(Appointment appointment)
    {
        SQLiteDatabase sqliteDb = this.getWritableDatabase();
        ContentValues cvAppointment = new ContentValues();

        cvAppointment.put(Appointment.Title, appointment.getTitle());
        cvAppointment.put(Appointment.Date, DateTimeHelper.dateToString(appointment.getDate()));
        cvAppointment.put(Appointment.Time, DateTimeHelper.timeToString(appointment.getTime()));
        cvAppointment.put(Appointment.Description, appointment.getDescription());

        sqliteDb.insert("Appointment", Appointment.Id, cvAppointment);
        sqliteDb.close();
    }


    public void appointment_Update(Appointment appointment)
    {
        SQLiteDatabase sqliteDb = this.getWritableDatabase();
        ContentValues cvAppointment = new ContentValues();

        cvAppointment.put(Appointment.Title, appointment.getTitle());
        cvAppointment.put(Appointment.Date, DateTimeHelper.dateToString(appointment.getDate()));
        cvAppointment.put(Appointment.Time, DateTimeHelper.timeToString(appointment.getTime()));
        cvAppointment.put(Appointment.Description, appointment.getDescription());

        sqliteDb.update("Appointment", cvAppointment, Appointment.Id + " = " + appointment.getId(), null);
        sqliteDb.close();
    }


    public Appointment appointment_GetById(Appointment appointment) throws ParseException
    {
        Appointment objAppointment;
        SQLiteDatabase sqlLiteDb = this.getReadableDatabase();
        Cursor curAppointment = sqlLiteDb.rawQuery("SELECT * FROM Appointment WHERE Id = "+ appointment.getId(),null);

        objAppointment = this.fillAppointment(curAppointment);

        curAppointment.close();
        sqlLiteDb.close();

        return objAppointment;
    }


    public void appointment_DeleteById(Appointment appointment)
    {
        SQLiteDatabase sqlLiteDb = this.getWritableDatabase();

        sqlLiteDb.delete("Appointment", Appointment.Id + " = " + appointment.getId(), null);
        sqlLiteDb.close();
    }


    public Appointments appointments_ViewAllByDate(Appointment appointment) throws ParseException
    {
        Appointments objAppointments;

        SQLiteDatabase sqlLiteDb = this.getReadableDatabase();
        Cursor curAppointments = sqlLiteDb.query("Appointment", columns, Appointment.Date + " = '" + DateTimeHelper.dateToString(appointment.getDate()) +"'", null, null, null, Appointment.Date);

        objAppointments = this.fillAppointments(curAppointments);

        curAppointments.close();
        sqlLiteDb.close();

        return objAppointments;
    }


    public Appointments appointments_ViewAll()throws ParseException
    {
        Appointments objAppointments;

        SQLiteDatabase sqlLiteDb = this.getReadableDatabase();
        Cursor curAppointments = sqlLiteDb.query("Appointment", columns, null, null, null, null, "Id");

        objAppointments = this.fillAppointments(curAppointments);

        curAppointments.close();
        sqlLiteDb.close();

        return objAppointments;
    }


    public void appointments_DeleteAll()
    {
        SQLiteDatabase sqlLiteDb = this.getWritableDatabase();

        sqlLiteDb.delete("Appointment", null, null);
        sqlLiteDb.close();
    }


    public void appointments_DeleteAllByDate(Appointment appointment)
    {
        SQLiteDatabase sqlLiteDb = this.getReadableDatabase();

        sqlLiteDb.delete("Appointment", Appointment.Date + " = '" + DateTimeHelper.dateToString(appointment.getDate()) +"'",null);
        sqlLiteDb.close();
    }


    public boolean isTitleAndDateExists(Appointment appointment)
    {
        boolean isTitleAndDateExists;

        SQLiteDatabase sqlLiteDb = this.getReadableDatabase();
        Cursor curAppointment = sqlLiteDb.rawQuery("SELECT * FROM Appointment WHERE " +Appointment.Title+" = '"+ appointment.getTitle()+"' AND "+Appointment.Date+ " = '"+DateTimeHelper.dateToString(appointment.getDate()) +"'",null);

        isTitleAndDateExists = curAppointment.getCount()!= 0;

        curAppointment.close();
        sqlLiteDb.close();

        return isTitleAndDateExists;
    }


    public void appointment_DeleteDatabase(Context context)
    {
        context.deleteDatabase("AppointmentManagementSystem");
    }


}
