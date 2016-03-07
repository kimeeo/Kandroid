package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class NavigateTo extends BaseAction{

    public NavigateTo(Activity activity) {
        super(activity);
    }

    public void perform(long latitude,long longitude,String address) {

        try
        {
            String locationURL;
            if(latitude==0 || longitude==0)
                locationURL = "http://maps.google.com/maps?daddr="+latitude+","+longitude+"";
            else
                locationURL = "http://maps.google.com/maps?daddr="+address;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationURL));
            activity.startActivity(intent);
        }
        catch (Exception e)
        {

        }


    }

    public void perform(String latitude,String longitude,String address) {
        try
        {
            String locationURL;
            if(latitude!=null && longitude!=null)
                locationURL = "http://maps.google.com/maps?daddr="+latitude+","+longitude+"";
            else
                locationURL = "http://maps.google.com/maps?daddr="+address;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationURL));
            activity.startActivity(intent);
        }
        catch (Exception e)
        {

        }


    }
}
