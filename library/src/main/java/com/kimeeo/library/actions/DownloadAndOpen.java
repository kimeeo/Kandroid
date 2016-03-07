package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class DownloadAndOpen extends Download {
    public DownloadAndOpen(Activity activity)
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
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), getMimeType(file.getPath()));
        if(title==null)
            title= "Set Image as";

        activity.startActivity(Intent.createChooser(intent, title));



    }
    public void perform(File file) {
        perform(file,null);
    }


    public static String getMimeType(String filename) {

        String filenameArray[] = filename.split("\\.");
        String extension = filenameArray[filenameArray.length-1];
        if(extension.equals("apk"))
            return "application/vnd.android.package-archive";
        else if(extension.equals("pdf"))
            return "application/pdf";
        else if(extension.equals("txt"))
            return "text/plain";
        else if(extension.equals("csv"))
            return "text/csv";
        else if(extension.equals("xml"))
            return "text/xml";
        else if(extension.equals("htm"))
            return "text/html";
        else if(extension.equals("html"))
            return "text/html";
        else if(extension.equals("php"))
            return "text/php";
        else if(extension.equals("png"))
            return "image/*";
        else if(extension.equals("gif"))
            return "image/*";
        else if(extension.equals("jpg"))
            return "image/*";
        else if(extension.equals("jpeg"))
            return "image/*";
        else if(extension.equals("bmp"))
            return "image/*";
        else if(extension.equals("mp3"))
            return "audio/mp3";
        else if(extension.equals("wav"))
            return "audio/wav";
        else if(extension.equals("ogg"))
            return "audio/x-ogg";
        else if(extension.equals("mid"))
            return "audio/mid";
        else if(extension.equals("midi"))
            return "audio/midi";
        else if(extension.equals("amr"))
            return "audio/AMR";
        else if(extension.equals("mpeg"))
            return "video/mpeg";
        else if(extension.equals("3gp"))
            return "video/3gpp";
        else if(extension.equals("mp4"))
            return "video/*";
        else if(extension.equals("jar"))
            return "application/java-archive";
        else if(extension.equals("zip"))
            return "application/zip";
        else if(extension.equals("rar"))
            return "application/x-rar-compressed";
        else if(extension.equals("gz"))
            return "application/gzip";
        return "";
    }




    public static Boolean isVideoURL(String filename) {
        String filenameArray[] = filename.split("\\.");
        if(filenameArray!=null && filenameArray.length>1)
        {
            String extension = filenameArray[filenameArray.length-1];
            if(extension.equals("wav"))
                return true;
            else if(extension.equals("ogg"))
                return true;
            else if(extension.equals("mid"))
                return true;
            else if(extension.equals("midi"))
                return true;
            else if(extension.equals("amr"))
                return true;
            else if(extension.equals("mpeg"))
                return true;
            else if(extension.equals("3gp"))
                return true;
            else if(extension.equals("mp4"))
                return true;
        }
        return false;
    }


}
