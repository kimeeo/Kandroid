package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;

import java.io.File;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class MailTo extends BaseAction{

    private String chooserTitle="Mail Using";

    public MailTo(Activity activity) {
        super(activity);
    }

    public void perform(String url) {
        try
        {
            String link=url;
            if(url.startsWith("mailto:")==false)
                link="mailto:"+url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            activity.startActivity(browserIntent);
        }
        catch (Exception e)
        {

        }

    }

    public void perform(String[] to,String[] cc,String[] bcc,String subject,String body,boolean isHTML,File[] attachment,String shareWith) {
        try
        {

            ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(activity);

            if(attachment!=null && attachment.length!=0) {
                if(attachment.length==1)
                {
                    Uri uri = Uri.fromFile(attachment[0]);
                    activity.grantUriPermission(activity.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intentBuilder.setStream(uri);
                }
                else {
                    for (int i = 0; i < attachment.length; i++) {
                        Uri uri = Uri.fromFile(attachment[i]);
                        activity.grantUriPermission(activity.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intentBuilder.addStream(uri);
                    }
                }
            }


            intentBuilder.setType("text/plain");
            intentBuilder.setChooserTitle(getChooserTitle());

            if (subject != null)
                intentBuilder.setSubject(subject);

            if(to!=null && to.length!=0)
                intentBuilder.setEmailTo(to);

            if(cc!=null && cc.length!=0)
                intentBuilder.setEmailCc(cc);

            if(bcc!=null && bcc.length!=0)
                intentBuilder.setEmailCc(bcc);

            if(body!=null && body.indexOf("<a href")!=-1)
                isHTML=true;

            if (body != null && isHTML)
                intentBuilder.setHtmlText(body);
            else if (body != null)
                intentBuilder.setText(body);

            if(shareWith!=null)
                intentBuilder.getIntent().setPackage(shareWith);

            intentBuilder.startChooser();
        }
        catch (Exception e)
        {

        }

    }

    public String getChooserTitle() {
        return chooserTitle;
    }

    public void setChooserTitle(String chooserTitle) {
        this.chooserTitle = chooserTitle;
    }
}
