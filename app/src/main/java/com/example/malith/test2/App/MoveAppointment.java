package com.example.malith.test2.App;

/**
 * Created by Malith
 */

import java.sql.Time;
import java.util.Date;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.ParseException;
import android.os.Bundle;
import com.example.malith.test2.R;
import com.example.malith.test2.basepackage.Appointment;
import com.example.malith.test2.basepackage.Base;
import com.example.malith.test2.basepackage.CommonEnums.MessageType;
import com.example.malith.test2.assistance.DateTimeHelper;
import com.example.malith.test2.assistance.MessageHelper;


public class MoveAppointment extends Base {

    DatePicker dpSelectDateToMove;
    TimePicker tpSelectTimeToMove;
    TextView   tvMoveAppointmentTitleDetails;
    TextView   tvMoveAppointmentDescriptionDetails;
    String _strCurrentDate;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.moveappointment);

        this.setExtras();
        this.setControls();
        try
        {
            this.bindDataToControls();
        }
        catch (ParseException e)
        {
            MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(),MessageType.ErrorMessage, this);
        }
    }

    public void btnMoveAppoinment_Click(View view)
    {
        try
        {
            this.bindDataToObject();

            boolean isDateTimeOk = false;

            if(DateTimeHelper.isSelectedTimeLowerThanCurrentTime(this.getCurrentAppointment().getTime()))
            {
                if(!DateTimeHelper.isSelectedDateLowerThanCurrentDate(this.getCurrentAppointment().getDate()) &&
                        !DateTimeHelper.isSelectedDateEqualsToCurrentDate(this.getCurrentAppointment().getDate()))
                    isDateTimeOk = true;
                else
                {
                    MessageHelper.showMessage(this.getString(R.string.common_Error), this.getString(R.string.common_selectedTimeIsLower),
                            MessageType.ErrorMessage,this);
                    return;
                }
            }
            else
            {
                if(!DateTimeHelper.isSelectedDateLowerThanCurrentDate(this.getCurrentAppointment().getDate()))
                    isDateTimeOk = true;
                else
                {
                    MessageHelper.showMessage(this.getString(R.string.common_Error), this.getString(R.string.moveappointment_theSelectedDateIsLower),
                            MessageType.ErrorMessage,this);
                    return;
                }
            }
            if(isDateTimeOk)
            {
                this.getDataBaseHelper().appointment_Update(this.getCurrentAppointment());

                MessageHelper.showMessage(this.getString(R.string.common_success), this.getString(R.string.moveappointment_successfullyMoved),
                        String.format("'%s' ",this.getCurrentAppointment().getTitle()),this.getString(R.string.common_splitString),
                        MessageType.SuccessMessage, this);
            }
            else
                MessageHelper.showMessage(this.getString(R.string.common_Error), this.getString(R.string.common_selectedTimeIsLower),
                        MessageType.ErrorMessage,this);
        }
        catch(Exception ex)
        {
            MessageHelper.showMessage(this.getString(R.string.common_Error), ex.getMessage(),MessageType.ErrorMessage, this);
        }
    }

    public void btnMoveAppBack_Click(View view)
    {
        try
        {
            this.getCurrentAppointment().setDate(DateTimeHelper.stringToDate(this._strCurrentDate));
            this.finish();
        }
        catch(Exception e)
        {
            MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(),MessageType.ErrorMessage, this);
        }
    }

    private void setControls()
    {
        this.dpSelectDateToMove = (DatePicker)this.findViewById(R.id.dpSelectDateToMove);
        this.tpSelectTimeToMove = (TimePicker)this.findViewById(R.id.tpSelectTimeToMove);
        this.tvMoveAppointmentTitleDetails = (TextView)this.findViewById(R.id.tvMoveAppointmentTitleDetails);
        this.tvMoveAppointmentDescriptionDetails = (TextView)this.findViewById(R.id.tvMoveAppointmentDescriptionDetails);
    }

    private void setExtras()
    {
        Bundle bndMoveAppointment = this.getIntent().getExtras();

        if(bndMoveAppointment != null)
        {
            int intMoveAppointment = bndMoveAppointment.getInt(Appointment.Id);

            Appointment objAppointment = new Appointment();

            objAppointment.setId(intMoveAppointment);

            try
            {
                this.setCurrentAppointment(getDataBaseHelper().appointment_GetById(objAppointment));
            }
            catch (ParseException e)
            {
                MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(),MessageType.SuccessMessage, this);
            }
        }
    }

    private void bindDataToControls() throws ParseException
    {
        this._strCurrentDate = DateTimeHelper.dateToString(this.getCurrentAppointment().getDate());
        this.dpSelectDateToMove.init(this.getCurrentAppointment().getDate().getYear(), this.getCurrentAppointment().getDate().getMonth(),this.getCurrentAppointment().getDate().getDate(), null);

        this.tpSelectTimeToMove.setCurrentHour(this.getCurrentAppointment().getTime().getHours());
        this.tpSelectTimeToMove.setCurrentMinute(this.getCurrentAppointment().getTime().getMinutes());

        this.tvMoveAppointmentTitleDetails.setText(this.getCurrentAppointment().getTitle());
        this.tvMoveAppointmentDescriptionDetails.setText(this.getCurrentAppointment().getDescription());
    }

    private void bindDataToObject()
    {
        Date objDate = new Date(this.dpSelectDateToMove.getYear(),
                this.dpSelectDateToMove.getMonth(),this.dpSelectDateToMove.getDayOfMonth());

        Time objTime = new Time(this.tpSelectTimeToMove.getCurrentHour(), this.tpSelectTimeToMove.getCurrentMinute(), 0);

        this.getCurrentAppointment().setDate(objDate);
        this.getCurrentAppointment().setTime(objTime);
    }

}
