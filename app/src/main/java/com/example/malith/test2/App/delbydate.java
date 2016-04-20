package com.example.malith.test2.App;

/**
 * Created by Malith
 */

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import android.widget.AdapterView.OnItemClickListener;
import com.example.malith.test2.R;
import com.example.malith.test2.basepackage.Appointment;
import com.example.malith.test2.basepackage.Appointments;
import com.example.malith.test2.basepackage.Base;
import com.example.malith.test2.basepackage.CommonEnums.MessageType;
import com.example.malith.test2.assistance.DateTimeHelper;
import com.example.malith.test2.assistance.MessageHelper;

public class delbydate extends Base {

    TextView tvAppDeleteSelectedDate;
    ListView lvDeleteAppointments;
    EditText txtSelectedDeleteIndex;
    Appointments _objAppointments = new Appointments();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.deleteappointment);
        this.setCotrols();

        this.tvAppDeleteSelectedDate.setText(DateTimeHelper.dateToString(this.getCurrentAppointment().getDate()));

        this.bindData();
    }


    public void btnDeleteAppIndex_Click(View view)
    {
        if(!this.txtSelectedDeleteIndex.getText().toString().equals(""))
        {
            int intSelectedIndex = Integer.parseInt(this.txtSelectedDeleteIndex.getText().toString())-1;

            if((intSelectedIndex >= 0)&&(intSelectedIndex < (this._objAppointments.size())))
            {
                Appointment objAppointment = this._objAppointments.get(intSelectedIndex);

                MessageHelper.appointmentDeleteConfiremation(this.getString(R.string.common_messageTitle),
                        this.getString(R.string.deleteappointments_wouldLikeToDeleteEvent),
                        String.format("'%s'",objAppointment.getTitle()),this.getString(R.string.common_splitString), this);
            }
            else
                MessageHelper.showMessage(this.getString(R.string.common_Error), this.getString(R.string.deleteappointments_recordDoesNotExist), MessageType.ErrorMessage, this);
        }
        else
            MessageHelper.showMessage(this.getString(R.string.common_Error),this.getString(R.string.deleteappointments_selectAvalidIndex), MessageType.ErrorMessage, this);
    }


    public void btnBack_Click(View view)
    {
        this.finish();
    }

    OnItemClickListener lvDeleteAppointments_Click  = new OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view,int position, long id)
        {
            Appointment objAppointment = ((delbydate)getInstence())._objAppointments.get((int)id);

            MessageHelper.showMessage(objAppointment.getTitle(),objAppointment.getDescription(),
                    getInstence());
        }
    };


    private void setCotrols()
    {
        this.txtSelectedDeleteIndex = (EditText)this.findViewById(R.id.txtSelectedDeleteIndex);
        this.tvAppDeleteSelectedDate = (TextView)this.findViewById(R.id.tvAppDeleteSelectedDate);
        this.lvDeleteAppointments = (ListView)this.findViewById(R.id.lvDeleteAppointments);

        this.lvDeleteAppointments.setOnItemClickListener(this.lvDeleteAppointments_Click);
    }

    private String[] getTitles()
    {
        String[] strTitles = new String[this._objAppointments.size()];

        for(int index=0;index<this._objAppointments.size();index++)
        {
            Appointment objAppointment = this._objAppointments.get(index);

            strTitles[index] = Integer.toString(index+1) + ". " +
                    DateTimeHelper.timeToString(objAppointment.getTime()) +" " +objAppointment.getTitle();
        }
        return strTitles;
    }


    private void bindData()
    {
        try
        {
            this._objAppointments = this.getDataBaseHelper().appointments_ViewAllByDate(this.getCurrentAppointment());

            this.lvDeleteAppointments.setAdapter(new ArrayAdapter<String>(delbydate.this,
                    android.R.layout.simple_list_item_1,this.getTitles()));

            if(!this._objAppointments.HasRecords())
                MessageHelper.showMessage(this.getString(R.string.common_messageTitle), this.getString(R.string.deleteappointments_noRecordsFoundForTheDate) + DateTimeHelper.dateToString(this.getCurrentAppointment().getDate()), this);
        }
        catch (ParseException e)
        {
            MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(), MessageType.ErrorMessage, this);
        }
    }

    public void deleteMessageById()
    {
        try
        {
            int intSelectedIndex = Integer.parseInt(this.txtSelectedDeleteIndex.getText().toString())-1;

            Appointment objAppointment = this._objAppointments.get(intSelectedIndex);

            this.getDataBaseHelper().appointment_DeleteById(objAppointment);

            this.bindData();

            MessageHelper.showMessage(this.getString(R.string.common_success),this.getString(R.string.deleteappointments_successfullyDeleted),
                    String.format("'%s'",objAppointment.getTitle()),this.getString(R.string.common_splitString),
                    MessageType.SuccessMessage, this);

        }
        catch(Exception e)
        {
            MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(), MessageType.ErrorMessage, this);
        }
    }

}
