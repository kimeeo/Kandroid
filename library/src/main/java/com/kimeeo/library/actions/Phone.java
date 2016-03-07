package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class Phone extends BaseAction{

    public Phone(Activity activity) {
        super(activity);
    }

    public void perform(String phoneNo) {

        try
        {
            if(phoneNo!=null) {
                String link = "tel:" + phoneNo;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                activity.startActivity(browserIntent);
            }
        }
        catch (Exception e)
        {

        }


    }
}
