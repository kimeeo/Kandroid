package com.kimeeo.library.actions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class ShowBusyDialog extends BaseAction{

    public ShowBusyDialog(Activity activity) {
        super(activity);
    }

    public void clear()
    {
        hideBusy();
        super.clear();
    }

    Runnable runnablelocal = new Runnable() {
        @Override
        public void run() {
            hideBusy();
        }
    };
    Handler handler;
    private ProgressDialog progressDialog;
    public void perform(String msg) {
        perform(msg,-1);
    }
    public void perform(String msg, Integer duration) {
        perform(msg,null,duration);
    }
    public void perform(String msg,String details, Integer duration) {

        try
        {
            hideBusy();
            progressDialog= new ProgressDialog(activity);
            progressDialog.setMessage(msg);
            if(details!=null)
                progressDialog.setMessage(details);
            progressDialog.show();

            if(handler!=null)
            {
                handler.removeCallbacks(runnablelocal);
                handler = null;
            }

            if(duration!=-1)
            {
                handler = new Handler();
                handler.postDelayed(runnablelocal, duration);
            }
        }
        catch (Exception e)
        {

        }
    }

    public void hideBusy() {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
