package com.example.malith.test2.basepackage;

/**
 * Created by Malith
 */


import java.util.ArrayList;


@SuppressWarnings("serial")
public class Appointments extends ArrayList<Appointment> {


    public boolean HasRecords()
    {
        return this.size() > 0;
    }

    public Appointments getSearchResults(String searchText)
    {
        Appointments objAppointments = new Appointments();

        searchText = searchText.toLowerCase();

        if(!searchText.equals(""))
        {
            for(int index=0;index<this.size();index++)
            {
                Appointment objTempAppointment = this.get(index);

                if(objTempAppointment.getDescription().toLowerCase().contains(searchText))
                    objAppointments.add(objTempAppointment);

                else if(objTempAppointment.getTitle().toLowerCase().contains(searchText))
                    objAppointments.add(objTempAppointment);
            }
            return objAppointments;
        }
        else
            return this;
    }

}
