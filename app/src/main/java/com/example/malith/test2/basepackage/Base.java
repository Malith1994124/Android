package com.example.malith.test2.basepackage;

/**
 * Created by Malith
 */


import android.app.Activity;

import com.example.malith.test2.datamanager.DataBaseHelper;

public class Base extends Activity {

    private final Base objThis = this;

    public Base getInstence()
    {

        return this.objThis;
    }



    private static DataBaseHelper _objDataBaseHelper = null;

    public DataBaseHelper getDataBaseHelper()
    {
        if (_objDataBaseHelper == null)
            _objDataBaseHelper= new DataBaseHelper(this);

        return _objDataBaseHelper;
    }


    private static Appointment _objCurrentAppointment = new Appointment();

    public void setCurrentAppointment(Appointment currentAppointment)
    {
        _objCurrentAppointment = currentAppointment;
    }

    public Appointment getCurrentAppointment()
    {

        return _objCurrentAppointment;
    }

    private static boolean _blnIsInSearchUpdateEditMode = false;

    public void setIsInSearchUpdateEditMode(boolean isInSearchUpdateEditMode)
    {
        _blnIsInSearchUpdateEditMode = isInSearchUpdateEditMode;
    }

    public boolean getIsInSearchUpdateEditMode()
    {
        return _blnIsInSearchUpdateEditMode;
    }

}
