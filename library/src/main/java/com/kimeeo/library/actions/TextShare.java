package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.text.Html;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class TextShare extends BaseAction{

    private String chooserTitle="Share with...";

    public TextShare(Activity activity) {
        super(activity);
    }

    public void perform(String data,String subject) {
        perform(data,subject,false,null);
    }
    public void perform(String data,String subject,boolean isHTML,String shareWith) {
        try
        {
            if(data!=null && data.equals("")==false)
            {

                ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(activity);
                intentBuilder.setType("text/plain");
                intentBuilder.setChooserTitle(getChooserTitle());

                if (subject != null)
                    intentBuilder.setSubject(subject);

                if(data.indexOf("<a href")!=-1)
                    isHTML=true;

                if (data != null && isHTML)
                    intentBuilder.setHtmlText(data);
                else if (data != null)
                    intentBuilder.setText(data);

                if(shareWith!=null)
                    intentBuilder.getIntent().setPackage(shareWith);

                intentBuilder.startChooser();

            }
        }
        catch (Exception e)
        {

        }
    }

    public void perform(String data) {
        perform(data,null);
    }

    public String getChooserTitle() {
        return chooserTitle;
    }

    public void setChooserTitle(String chooserTitle) {
        this.chooserTitle = chooserTitle;
    }
}
