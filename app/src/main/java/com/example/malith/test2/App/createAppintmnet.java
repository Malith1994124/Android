package com.example.malith.test2.App;

/**
 * Created by Malith
 */
import java.sql.Time;
import java.util.Date;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import java.text.ParseException;
import com.example.malith.test2.R;
import com.example.malith.test2.basepackage.Appointment;
import com.example.malith.test2.basepackage.Base;
import com.example.malith.test2.basepackage.CommonEnums.MessageType;
import com.example.malith.test2.assistance.DateTimeHelper;
import com.example.malith.test2.assistance.MessageHelper;


public class createAppintmnet extends Base
 {

     EditText txtTitle;
     TimePicker tpAppointmentTime;
     EditText txtDetails;
     Button btnClearAppointment;
     boolean _isUpdateMode = false;
     boolean _isInSearch = false;
     String _strCurrentDate;

     @Override
     public void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);

         this.setContentView(R.layout.createappointment);

         this.setExtras();
         this.setControls();

         if(this._isUpdateMode || this._isInSearch)
         {
             this.btnClearAppointment.setEnabled(false);
             this.bindData();
         }
     }

     public void btnSaveData_Click(View view) throws ParseException
     {
         if(!this.isFieldsEmpty())
         {
             boolean isDateTimeOk = false;

             this.bindDataToObject();

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
                 if(!this._isUpdateMode && !this._isInSearch) {
                     this.getDataBaseHelper().appointment_Insert(this.getCurrentAppointment());
                     this.showSuccessMessageOnInsertUpdate(this.getString(R.string.enterappointment_added));
                     this.clearForm();
                 }
                 else if(this._isUpdateMode || this._isInSearch)
                 {
                     this.getDataBaseHelper().appointment_Update(this.getCurrentAppointment());
                     this.showSuccessMessageOnInsertUpdate(this.getString(R.string.enterappointment_updated));
                 }
             }
             else
                 MessageHelper.showMessage(this.getString(R.string.common_Error), this.getString(R.string.common_selectedTimeIsLower),
                         MessageType.ErrorMessage,this);
         }
         else
             MessageHelper.showMessage(this.getString(R.string.common_Error), this.getString(R.string.enterappointment_titleOrDescriptionCantBeEmpty),
                     MessageType.ErrorMessage,this);
     }

     public void btnClearAppointment_Click(View view)
     {
         this.clearForm();
     }

     public void btnBack_Click(View view)
     {
         try
         {
             if(this._isInSearch || this._isUpdateMode)
                 this.getCurrentAppointment().setDate(DateTimeHelper.stringToDate(this._strCurrentDate));
         }
         catch(Exception e)
         {
             MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(), MessageType.ErrorMessage, this);
         }
         this.finish();
     }

     private void clearForm()
     {
         this.txtTitle.setText("");
         this.txtDetails.setText("");

         Date objDate = new Date();

         this.tpAppointmentTime.setCurrentHour(objDate.getHours());
         this.tpAppointmentTime.setCurrentMinute(objDate.getMinutes());

         this.getCurrentAppointment().setTitle("");
         this.getCurrentAppointment().setTime(new Time(0));
         this.getCurrentAppointment().setDescription("");
     }

     private boolean isFieldsEmpty()
     {
         if(this.txtTitle.getText().toString().equals("") || this.txtDetails.getText().toString().equals(""))
             return true;
         else
             return false;
     }

     private void setControls()
     {
         this.txtTitle = (EditText)this.findViewById(R.id.txtTitle);
         this.txtDetails = (EditText)this.findViewById(R.id.txtDetails);

         this.tpAppointmentTime = (TimePicker)this.findViewById(R.id.tpAppointmentTime);

         this.btnClearAppointment =(Button)this.findViewById(R.id.btnClearAppointment);
     }

     private void bindData()
     {
         this._strCurrentDate = DateTimeHelper.dateToString(this.getCurrentAppointment().getDate());
         this.txtTitle.setText(this.getCurrentAppointment().getTitle());
         this.txtDetails.setText(this.getCurrentAppointment().getDescription());

         this.tpAppointmentTime.setCurrentHour(this.getCurrentAppointment().getTime().getHours());
         this.tpAppointmentTime.setCurrentMinute(this.getCurrentAppointment().getTime().getMinutes());
     }


     private void setExtras()
     {
         Bundle bndAddAppointment = this.getIntent().getExtras();

         if(bndAddAppointment != null)
         {
             this._isUpdateMode = bndAddAppointment.getBoolean(Appointment.IsUpdate);
             this._isInSearch = bndAddAppointment.getBoolean(Appointment.IsInSearch);
             int id = bndAddAppointment.getInt(Appointment.Id);

             if(this._isUpdateMode || this._isInSearch)
             {
                 this.getCurrentAppointment().setId(id);
                 try
                 {
                     this.setCurrentAppointment(this.getDataBaseHelper().appointment_GetById(this.getCurrentAppointment()));
                 }
                 catch (Exception e)
                 {
                     MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(), MessageType.ErrorMessage, this);
                 }
             }
         }
     }


     private void bindDataToObject()
     {
         this.getCurrentAppointment().setTitle(this.txtTitle.getText().toString());
         this.getCurrentAppointment().setTime(new Time(this.tpAppointmentTime.getCurrentHour(),this.tpAppointmentTime.getCurrentMinute(),0));
         this.getCurrentAppointment().setDescription(this.txtDetails.getText().toString());
     }

     private void showSuccessMessageOnInsertUpdate(String status)
     {
         MessageHelper.showMessage(this.getString(R.string.common_success),this.getString(R.string.enterappointment_successfullEntry) +String.format(" %s.",status),
                 String.format("'%s'", this.getCurrentAppointment().getTitle()),this.getString(R.string.common_splitString), MessageType.SuccessMessage,this);
     }


}
