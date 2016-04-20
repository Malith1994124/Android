package com.example.malith.test2.App;

/**
 * Created by Malith
 */


import java.util.Date;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.text.ParseException;
import android.content.Intent;
import android.content.Context;
import com.example.malith.test2.R;
import com.example.malith.test2.basepackage.Appointment;
import com.example.malith.test2.basepackage.Base;
import com.example.malith.test2.basepackage.CommonEnums.MessageType;
import com.example.malith.test2.assistance.DateTimeHelper;
import com.example.malith.test2.assistance.MessageHelper;


public class systemactivity extends Base {


    DatePicker _dpAppointment;
    Dialog _deleteDialog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.setControls();
    }

    public void btnAddAppoinment_Click(View view) throws ParseException
    {
        Appointment objAppointment = new Appointment();

        objAppointment.setDate(new Date(this._dpAppointment.getYear(), this._dpAppointment.getMonth(), this._dpAppointment.getDayOfMonth()));

        if(DateTimeHelper.isSelectedDateLowerThanCurrentDate(objAppointment.getDate()))
            MessageHelper.showMessage(this.getString(R.string.common_Error), "You have been restricted to select a passed date",MessageType.ErrorMessage, this);

        else
        {
            this.setCurrentAppointment(objAppointment);

            Intent itEnterAppointment = new Intent((Context)this,new createAppintmnet().getClass());

            this.startActivity(itEnterAppointment);
        }
    }

    public void btnViewEditAppoinment_Click(View view)
    {
        this.getCurrentAppointment().setDate(new Date(this._dpAppointment.getYear(), this._dpAppointment.getMonth(), this._dpAppointment.getDayOfMonth()));

        Bundle bndViewEditAppointment = new Bundle();

        bndViewEditAppointment.putBoolean(Appointment.IsInViewEdit,true);

        Intent itViewEditAppointments = new Intent((Context)this, new ViewEditAppointments().getClass());

        itViewEditAppointments.putExtras(bndViewEditAppointment);

        this.startActivity(itViewEditAppointments);
    }


    public void btnDeleteAppoinment_Click(View view)
    {
        this.getCurrentAppointment().setDate(new Date(this._dpAppointment.getYear(), this._dpAppointment.getMonth(), this._dpAppointment.getDayOfMonth()));

        MessageHelper.showDeleteOptions(this);
    }


    public void btnMoveAppointment_Click(View view)
    {
        this.getCurrentAppointment().setDate(new Date(this._dpAppointment.getYear(), this._dpAppointment.getMonth(), this._dpAppointment.getDayOfMonth()));

        Bundle bndMoveAppointment = new Bundle();

        bndMoveAppointment.putBoolean(Appointment.IsInViewEdit,false);

        Intent itViewEditAppointment = new Intent((Context)this,new ViewEditAppointments().getClass());

        itViewEditAppointment.putExtras(bndMoveAppointment);

        this.startActivity(itViewEditAppointment);
    }


    public void btnSearchAppoinment_Click(View view)
    {
        Intent itSearchAppointment = new Intent(this,new SearchAppointments().getClass());

        this.startActivity(itSearchAppointment);
    }


    public void btnClearAllRecords_Click(View view)
    {
        MessageHelper.databaseDeleteConfiremation("", this.getString(R.string.main_doYouWantToDeleteRecords), this);
    }


    public void btnExit_Click(View view)
    {
        this.finish();
    }


    public void deleteAppointments(int checkedId,RadioGroup group,Dialog dialog)
    {
        RadioButton rbtnChecked  = (RadioButton)group.findViewById(checkedId);

        if(rbtnChecked != null)
        {
            if(rbtnChecked.isChecked())
            {
                int intSelectedAnswerIndex = Integer.parseInt(rbtnChecked.getHint().toString());

                if(intSelectedAnswerIndex == 0)
                {
                    this.getDataBaseHelper().appointments_DeleteAllByDate(this.getCurrentAppointment());

                    MessageHelper.showMessage(this.getString(R.string.common_success),this.getString(R.string.main_allAppointmentsDeleteByDate),
                            String.format("'%s'",DateTimeHelper.dateToString(this.getCurrentAppointment().getDate())),
                            this.getString(R.string.common_splitString),this.getInstence());
                }
                else if(intSelectedAnswerIndex == 1)
                {
                    Intent itEnterAppointment = new Intent((Context)this,new delbydate().getClass());

                    startActivity(itEnterAppointment);
                }
            }
        }
    }

    public void deleteAllRecords()
    {
        try
        {
            this.getDataBaseHelper().appointments_DeleteAll();

            MessageHelper.showMessage(this.getString(R.string.common_success), this.getString(R.string.main_allRecsDeleted),MessageType.SuccessMessage, this);
        }
        catch(Exception e)
        {
            MessageHelper.showMessage(this.getString(R.string.common_Error), this.getString(R.string.main_allRecsDeletedError),MessageType.ErrorMessage, this);
        }
    }


    private void setControls()
    {
        this._dpAppointment = (DatePicker)this.findViewById(R.id.dpAppointment);
    }

}
