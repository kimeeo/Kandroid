package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;

import java.io.File;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class ImageSet extends Download {
    private String chooserTitle;

    public ImageSet(Activity activity)
    {
        super(activity);
    }

    public void perform(String link,String location,final String title, boolean showProgress, final String success,final String fail,final DownloadResult downloadResult) {

        DownloadResult operation= new DownloadResult() {
            public void success(File file) {

                Uri sendUri = Uri.fromFile(file);
                perform(sendUri,title,null);
                if (downloadResult != null)
                    downloadResult.success(file);
            }

            public void fail(Object data)
            {
                if (downloadResult != null)
                    downloadResult.fail(data);
            }
        };
        perform(link, location, showProgress, success, fail, operation);
    }
    public void perform(Uri sendUri,String subject,String shareWith) {
        try
        {
            /*
            activity.grantUriPermission(activity.getPackageName(), sendUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(activity);
            intentBuilder.setType("image/*");
            intentBuilder.getIntent().putExtra("mimeType", "image/*");
            intentBuilder.getIntent().setDataAndType(sendUri, "image/*");
            intentBuilder.setStream(sendUri);
            intentBuilder.setChooserTitle(getChooserTitle());

            if (subject != null)
                intentBuilder.setSubject(subject);
            if(shareWith!=null)
                intentBuilder.getIntent().setPackage(shareWith);
            intentBuilder.startChooser();
            */



            activity.grantUriPermission(activity.getPackageName(), sendUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.setDataAndType(sendUri, "image/*");
            intent.putExtra("mimeType", "image/*");

            if(shareWith!=null)
                intent.setPackage(shareWith);

            activity.startActivity(Intent.createChooser(intent, getChooserTitle()));
        }
        catch (Exception e)
        {

        }
    }
    public void perform(Uri sendUri) {
        perform(sendUri, null,null);
    }
    public void perform(File file)
    {
        perform(Uri.fromFile(file),null,null);
    }
    public void perform(File file,String title)
    {
        perform(Uri.fromFile(file),title,null);
    }


    public String getChooserTitle() {
        return chooserTitle;
    }

    public void setChooserTitle(String chooserTitle) {
        this.chooserTitle = chooserTitle;
    }
}
