package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class Toast extends BaseAction{

    public Toast(Activity activity) {
        super(activity);
    }

    public void perform(String msg,boolean isLong) {
        try
        {
            if(msg!=null && msg.equals("")==false)
            {
                if(isLong)
                    android.widget.Toast.makeText(activity, msg, android.widget.Toast.LENGTH_LONG).show();
                else
                    android.widget.Toast.makeText(activity, msg, android.widget.Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {

        }
    }
    public void perform(String msg) {
        perform(msg,true);
    }
    public void perform(String msg,int duration) {
        try
        {
            if(msg!=null && msg.equals("")==false)
                android.widget.Toast.makeText(activity, msg, duration).show();
        }
        catch (Exception e)
        {

        }
    }
}
