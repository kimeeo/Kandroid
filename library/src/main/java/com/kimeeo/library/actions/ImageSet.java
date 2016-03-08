package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class ImageSet extends Download {
    public ImageSet(Activity activity)
    {
        super(activity);
    }

    public void perform(String link,String location,final String title, boolean showProgress, final String success,final String fail,final DownloadResult downloadResult) {

        DownloadResult operation= new DownloadResult() {
            public void success(File file) {

                Uri sendUri = Uri.fromFile(file);
                perform(sendUri,title);
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
    public void perform(Uri sendUri,String title) {
        try
        {
            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.setDataAndType(sendUri, "image/*");
            intent.putExtra("mimeType", "image/*");
            if(title==null)
                title= "Set Image as";
            activity.startActivity(Intent.createChooser(intent, title));
        }
        catch (Exception e)
        {

        }
    }
    public void perform(Uri sendUri) {
        perform(sendUri, null);
    }
    public void perform(File file)
    {
        perform(Uri.fromFile(file),null);
    }
    public void perform(File file,String title)
    {
        perform(Uri.fromFile(file),title);
    }



}
