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
            String locationURL = null;
            if(latitude!=-1 && longitude!=-1)
                locationURL = "http://maps.google.com/maps?daddr="+latitude+","+longitude+"";
            else if(address!=null)
                locationURL = "http://maps.google.com/maps?daddr="+address;

            if(locationURL!=null) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationURL));
                activity.startActivity(intent);
            }
        }
        catch (Exception e)
        {

        }
    }

    public void perform(String latitude,String longitude,String address) {
        try
        {
            long latitude1;
            long longitude1;
            if(latitude!=null && longitude!=null) {
                latitude1 = Long.parseLong(latitude);
                longitude1 = Long.parseLong(longitude);
                perform(latitude1, longitude1, address);
            }
        }
        catch (Exception e)
        {
            perform(-1, -1, address);
        }
    }

    public void perform(String address) {
        perform(-1, -1, address);
    }
    public void perform(long latitude,long longitude) {
        perform(latitude,longitude,null);
    }

    public void perform(String latitude,String longitude) {
        perform(latitude,longitude,null);
    }
}
