package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class ImageShare extends Download {
    public ImageShare(Activity activity)
    {
        super(activity);
    }

    public void perform(String link,String location,final String title, boolean showProgress, final String success,final String fail,final DownloadResult downloadResult) {

        DownloadResult operation= new DownloadResult() {
            public void success(File file) {
                perform(file,title);
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
    public void perform(File file,String title) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getPath()));
        if(title==null)
            title= "Share Image";
        activity.startActivity(Intent.createChooser(shareIntent, title));
    }
    public void perform(File file) {
        perform(file,null);
    }
}
