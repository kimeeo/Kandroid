package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;

import java.io.File;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class ImageShare extends Download {
    public ImageShare(Activity activity)
    {
        super(activity);
    }

    public void perform(String link, String location, final String title, final String text, boolean showProgress, final String success, final String fail, final DownloadResult downloadResult) {
        DownloadResult operation= new DownloadResult() {
            public void success(File file) {
                perform(file, title, text);
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

    public void perform(File file, String title, String text) {
        try
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getPath()));


            if (text != null)
                shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(text));

            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if(title==null)
                title= "Share Image";
            activity.startActivity(Intent.createChooser(shareIntent, title));
        }
        catch (Exception e)
        {

        }
    }

    public void perform(File file, String title) {
        perform(file, title, null);
    }

    public void perform(Uri uri, String title, String text) {
        File file = new File(getPath(activity, uri));
        perform(file, title, text);
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
}
