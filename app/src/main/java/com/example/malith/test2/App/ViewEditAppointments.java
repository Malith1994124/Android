package com.example.malith.test2.App;

/**
 * Created by Malith
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import java.text.ParseException;
import com.example.malith.test2.R;
import com.example.malith.test2.basepackage.Appointment;
import com.example.malith.test2.basepackage.Appointments;
import com.example.malith.test2.basepackage.Base;
import com.example.malith.test2.basepackage.CommonEnums.MessageType;
import com.example.malith.test2.assistance.DateTimeHelper;
import com.example.malith.test2.assistance.MessageHelper;

public class ViewEditAppointments extends Base  {


    ListView lvViewAppointments;
    boolean isInViewEdit = true;
    Appointments _objAppointments = new Appointments();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.view);

        this.setExtras();
        this.setControls();
           }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.bindData();
    };

    public void btnViewByDateBack_Click(View view)
    {
        this.finish();
    }

    OnItemClickListener lvViewAppointments_Click = new OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent,View view,int position, long id)
        {
            Bundle bndAddAppointment = new Bundle();

            bndAddAppointment.putInt(Appointment.Id, _objAppointments.get((int) id).getId());

            if(isInViewEdit)
            {
                Intent itAddAppointment = new Intent((Context)getInstence() ,new createAppintmnet().getClass());

                bndAddAppointment.putBoolean(Appointment.IsUpdate, true);

                itAddAppointment.putExtras(bndAddAppointment);
                startActivity(itAddAppointment);
            }
            else
            {
                Intent itMoveAppointment = new Intent((Context)getInstence(),new MoveAppointment().getClass());

                itMoveAppointment.putExtras(bndAddAppointment);
                startActivity(itMoveAppointment);
            }
        }
    };

    private void setControls()
    {
        this.lvViewAppointments = (ListView)this.findViewById(R.id.lvViewAppointments);

        this.lvViewAppointments.setOnItemClickListener(this.lvViewAppointments_Click);
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

            if(this._objAppointments.HasRecords())
                this.lvViewAppointments.setAdapter(new ArrayAdapter<String>(ViewEditAppointments.this,
                        android.R.layout.simple_list_item_1,this.getTitles()));
            else
            {
                this.lvViewAppointments.setAdapter(null);
                MessageHelper.showMessage(this.getString(R.string.common_messageTitle), this.getString(R.string.viewappointments_noRecordsFoundForDate) + String.format(" %s", DateTimeHelper.dateToString(this.getCurrentAppointment().getDate())), this);
            }
        }
        catch (ParseException e)
        {
            MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(), MessageType.ErrorMessage, this);
        }
    }


    private void setExtras()
    {
        Bundle bndViewEdit = this.getIntent().getExtras();

        if(bndViewEdit != null)
            this.isInViewEdit = bndViewEdit.getBoolean(Appointment.IsInViewEdit);
    }

}
