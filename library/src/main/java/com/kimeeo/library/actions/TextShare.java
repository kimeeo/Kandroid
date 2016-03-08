package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class TextShare extends BaseAction{

    public TextShare(Activity activity) {
        super(activity);
    }

    public void perform(String data,String title) {
        try
        {
            if(data!=null && data.equals("")==false) {
                if(title==null)
                    title ="Share with...";
                Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, data);
                activity.startActivity(Intent.createChooser(sendIntent, title));
            }
        }
        catch (Exception e)
        {

        }
    }
    public void perform(String data) {
        perform(data,null);
    }
}
