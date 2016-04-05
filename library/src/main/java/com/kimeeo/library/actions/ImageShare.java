package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.text.Html;

import java.io.File;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class ImageShare extends Download {
    private String chooserTitle="Share Image";

    public ImageShare(Activity activity)
    {
        super(activity);
    }

    public void perform(String link, String location, final String title, final String text, boolean showProgress, final String success, final String fail, final DownloadResult downloadResult) {
        perform(link, location, title, text, false, showProgress, success, fail, downloadResult);
    }


    public void perform(String link, String location, final String title, final String text,final boolean isHTML, boolean showProgress, final String success, final String fail, final DownloadResult downloadResult) {
        DownloadResult operation= new DownloadResult() {
            public void success(File file) {
                perform(file, title, text,isHTML,null);
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

    public void perform(String link, String location, final String title, boolean showProgress, final String success, final String fail, final DownloadResult downloadResult) {
        perform(link, location, title, null, showProgress, success, fail, downloadResult);
    }

    public void perform(File file, String subject, String text,boolean isHTML,String shareWith) {
        Uri uri= Uri.fromFile(file);
        activity.grantUriPermission(activity.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(activity);
        intentBuilder.setType("image/*");
        intentBuilder.setStream(uri);
        intentBuilder.setChooserTitle(getChooserTitle());

        if (subject != null)
            intentBuilder.setSubject(subject);

        if(text!=null && text.indexOf("<a href")!=-1)
            isHTML=true;

        if (text != null && isHTML)
            intentBuilder.setHtmlText(text);
        else if (text != null)
            intentBuilder.setText(text);

        if(shareWith!=null)
            intentBuilder.getIntent().setPackage(shareWith);

        intentBuilder.startChooser();

        //activity.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }

    public void perform(File file, String title) {
        perform(file, title, null,false,null);
    }

    public void perform(Uri uri, String title, String text) {
        File file = new File(getPath(activity, uri));
        perform(file, title, text,false,null);
    }
    public void perform(Uri uri,String title) {
        File file = new File(getPath(activity,uri));
        perform(file,title);
    }
    public void perform(File file)
    {
        perform(file,null);
    }
    public void perform(Uri uri) {
        perform(uri,null);
    }

    public String getChooserTitle() {
        return chooserTitle;
    }

    public void setChooserTitle(String chooserTitle) {
        this.chooserTitle = chooserTitle;
    }
}
