package com.example.malith.test2.App;

/**
 * Created by Malith
 */

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import java.text.ParseException;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.example.malith.test2.R;
import com.example.malith.test2.basepackage.Appointment;
import com.example.malith.test2.basepackage.Appointments;
import com.example.malith.test2.basepackage.Base;
import com.example.malith.test2.basepackage.CommonEnums.MessageType;
import com.example.malith.test2.assistance.DateTimeHelper;
import com.example.malith.test2.assistance.MessageHelper;

public class SearchAppointments extends Base  {

    TextView tvNoSearchResults;
    EditText txtSearchText;
    ListView lvSearchResults;

    Appointments _objAppointments = new Appointments();
    Appointments _objSearchResultsAppointments = new Appointments();

    ArrayList<HashMap<String,String>> alSearchResults = new ArrayList<HashMap<String,String>>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.searchappointment);
        this.setControls();
        this.bindAllAppointments();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();

        this.bindAllAppointments();
        this.searchText(this.txtSearchText.getText().toString());
    }

    public void btnAppointmentSearchBack_Click(View view)
    {
        this.finish();
    }


    public void btnSearchAppointments_Click(View view)
    {
        this.searchText(this.txtSearchText.getText().toString());
    }

    OnItemClickListener lvSearchResults_Click = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id)
        {
            setCurrentAppointment(_objSearchResultsAppointments.get((int)id));

            MessageHelper.showSearchEditMoveOptions(getInstence());
        }
    };

    private void setControls()
    {
        this.tvNoSearchResults = (TextView)this.findViewById(R.id.tvNoSearchResults);
        this.txtSearchText = (EditText)this.findViewById(R.id.txtSearchText);
        this.lvSearchResults = (ListView)this.findViewById(R.id.lvSearchResults);

        this.lvSearchResults.setOnItemClickListener(this.lvSearchResults_Click);
    }


    private void bindAllAppointments()
    {
        try
        {
            this._objAppointments = this.getDataBaseHelper().appointments_ViewAll();
        }
        catch (ParseException e)
        {
            MessageHelper.showMessage(this.getString(R.string.common_Error), e.getMessage(),MessageType.ErrorMessage ,this);
        }
    }

    private void populateSearchResults()
    {
        this.alSearchResults.clear();

        for(int index=0;index < this._objSearchResultsAppointments.size();index++)
        {
            HashMap<String,String> hmSearchResults = new HashMap<String,String>();

            hmSearchResults.put(Appointment.Title,String.format("%s %s  ",this.getString(R.string.common_Title),this._objSearchResultsAppointments.get(index).getTitle()));
            hmSearchResults.put(Appointment.Description,String.format("%s %s  ",this.getString(R.string.common_DescriptionAppointment),this._objSearchResultsAppointments.get(index).getDescription()));
            hmSearchResults.put(Appointment.Date,String.format("%s %s  ", this.getString(R.string.common_enterDateOfAppointment), DateTimeHelper.dateToString(this._objSearchResultsAppointments.get(index).getDate())));
            hmSearchResults.put(Appointment.Time,String.format("%s %s  ",this.getString(R.string.common_enterTimeOfAppointment),DateTimeHelper.timeToString(this._objSearchResultsAppointments.get(index).getTime())));

            this.alSearchResults.add(hmSearchResults);
        }
    }

    private void searchText(String searchText)
    {
        this._objSearchResultsAppointments =
                this._objAppointments.getSearchResults(searchText);

        if(this._objSearchResultsAppointments.HasRecords())
        {
            SimpleAdapter adapter = new SimpleAdapter(this,alSearchResults, R.layout.searchrow,
                    new String[]{Appointment.Title,Appointment.Description,Appointment.Date,Appointment.Time},
                    new int[]{R.id.tvSearchTitle,R.id.tvSearchDescription,R.id.tvSearchDate,R.id.tvSearchTime});

            this.populateSearchResults();

            this.lvSearchResults.setAdapter(adapter);
            this.tvNoSearchResults.setVisibility(View.INVISIBLE);
        }
        else
        {
            this._objSearchResultsAppointments.clear();
            this.lvSearchResults.setAdapter(null);
            this.tvNoSearchResults.setVisibility(View.VISIBLE);
        }
    }

    public void searchEditMoveAppointments(int checkedId,RadioGroup group,Dialog dialog)
    {
        RadioButton rbtnChecked  = (RadioButton)group.findViewById(checkedId);

        if(rbtnChecked != null)
        {
            if(rbtnChecked.isChecked())
            {
                int intSelectedAnswerIndex = Integer.parseInt(rbtnChecked.getHint().toString());

                if(intSelectedAnswerIndex == 0)
                {
                    Bundle bndAddAppointment = new Bundle();
                    Intent itAddAppointment = new Intent((Context)getInstence() ,new createAppintmnet().getClass());

                    bndAddAppointment.putInt(Appointment.Id, getCurrentAppointment().getId());
                    bndAddAppointment.putBoolean(Appointment.IsUpdate, true);

                    itAddAppointment.putExtras(bndAddAppointment);
                    startActivity(itAddAppointment);
                }
                else if(intSelectedAnswerIndex == 1)
                {
                    Bundle bndAddAppointment = new Bundle();

                    Intent itMoveAppointment = new Intent((Context)getInstence(),new MoveAppointment().getClass());

                    bndAddAppointment.putInt(Appointment.Id, getCurrentAppointment().getId());
                    itMoveAppointment.putExtras(bndAddAppointment);
                    startActivity(itMoveAppointment);
                }
            }
        }
    }

}
