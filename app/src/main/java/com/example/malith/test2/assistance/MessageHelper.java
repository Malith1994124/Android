package com.example.malith.test2.assistance;

/**
 * Created by Malith
 */

import com.example.malith.test2.App.delbydate;
import com.example.malith.test2.App.systemactivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.example.malith.test2.R;
import com.example.malith.test2.App.SearchAppointments;
import com.example.malith.test2.basepackage.Base;
import com.example.malith.test2.basepackage.CommonEnums.MessageType;

public class MessageHelper {

    static Dialog _dialog;
    static Base _context;

    public static void showMessage(String title,String message,Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.setButton("OK",alertBoxLecture_Click);
        alertDialog.show();
    }


    public static void showMessage(String title,String message,MessageType messageType,Context context)
    {
        Dialog dialog = new Dialog(context);
        _dialog = dialog;

        dialog.setTitle(title);
        dialog.setContentView( R.layout.alert);
        dialog.setCancelable(true);

        TextView tvMessage = (TextView)dialog.findViewById(R.id.tvMessage);
        Button btnOK = (Button)dialog.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(btnOk_Click);
        tvMessage.setText(message);

        dialog.show();
    }

    public static void showMessage(String title,String message,String value,String splitString,Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message.replace(splitString, value));
        alertDialog.setCancelable(true);
        alertDialog.setButton("OK",alertBoxLecture_Click);
        alertDialog.show();
    }

    public static void showMessage(String title,String message,String value,String splitString,MessageType messageType,Context context)
    {
        Dialog dialog = new Dialog(context);
        _dialog = dialog;

        dialog.setTitle(title);
        dialog.setContentView( R.layout.alert);
        dialog.setCancelable(true);

        TextView tvMessage = (TextView)dialog.findViewById(R.id.tvMessage);
        Button btnOK = (Button)dialog.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(btnOk_Click);
        tvMessage.setText(message.replace(splitString, value));

        dialog.show();
    }

    public static void showDeleteOptions(Context context)
    {
        Dialog dialog = new Dialog(context);
        _dialog = dialog;
        _context = (Base)context;

        dialog.setContentView(R.layout.deleoptions);
        dialog.setCancelable(true);

        RadioGroup  rgDeleteOption = (RadioGroup)dialog.findViewById(R.id.rgDeleteOption);

        rgDeleteOption.setOnCheckedChangeListener(rgDeleteOption_CheckChanged);

        dialog.show();
    }

    public static void showSearchEditMoveOptions(Context context)
    {
        Dialog dialog = new Dialog(context);
        _dialog = dialog;
        _context = (Base)context;

        dialog.setContentView(R.layout.searchoptions);
        dialog.setCancelable(true);

        RadioGroup  rgSearchEditMoveOption = (RadioGroup)dialog.findViewById(R.id.rgSearchEditMoveOption);

        rgSearchEditMoveOption.setOnCheckedChangeListener(rgSearchEditMoveOption_CheckChanged);

        dialog.show();
    }

    public static void appointmentDeleteConfiremation(String title, String message,Context context)
    {
        _context = (Base)context;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes",appointmentDeleteConfiremationOK_Click);
        alertDialogBuilder.setNegativeButton("No",appointmentDeleteConfiremationNo_Click);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public static void appointmentDeleteConfiremation(String title, String message,String value,String splitString,Context context)
    {
        _context = (Base)context;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message.replace(splitString, value));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes",appointmentDeleteConfiremationOK_Click);
        alertDialogBuilder.setNegativeButton("No",appointmentDeleteConfiremationNo_Click);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public static void databaseDeleteConfiremation(String title, String message,Context context)
    {
        _context = (Base)context;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes",databaseDeleteConfiremationOK_Click);
        alertDialogBuilder.setNegativeButton("No",databaseDeleteConfiremationNo_Click);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    static OnClickListener btnOk_Click = new OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            _dialog.dismiss();
        }
    };


    static DialogInterface.OnClickListener alertBoxLecture_Click = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    };

    static OnCheckedChangeListener rgDeleteOption_CheckChanged = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            _dialog.dismiss();
            ((systemactivity)_context).deleteAppointments(checkedId, group, _dialog);
        }
    };

    static OnCheckedChangeListener rgSearchEditMoveOption_CheckChanged = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            _dialog.dismiss();
            ((SearchAppointments)_context).searchEditMoveAppointments(checkedId, group, _dialog);
        }
    };


    static DialogInterface.OnClickListener appointmentDeleteConfiremationOK_Click = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            ((delbydate)_context).deleteMessageById();
            dialog.dismiss();
        }
    };

    static DialogInterface.OnClickListener appointmentDeleteConfiremationNo_Click = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    };

       static DialogInterface.OnClickListener databaseDeleteConfiremationOK_Click = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            ((systemactivity)_context).deleteAllRecords();
            dialog.dismiss();
        }
    };


    static DialogInterface.OnClickListener databaseDeleteConfiremationNo_Click = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    };

}


