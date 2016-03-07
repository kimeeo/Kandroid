package com.kimeeo.library.actions;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class Download extends BaseAction{

    public Download(Activity activity) {
        super(activity);
    }

    private String folderLocation=null;
    private AQuery aq;
    private int progressBarType=PROGRESS_BAR_TYPE_NOTIFICATION;
    public static int PROGRESS_BAR_TYPE_NOTIFICATION=1;
    public static int PROGRESS_BAR_TYPE_POPUP=2;
    private Map<Integer,ProgressDialog> progressDialogMap=new HashMap<>();
    private File downloadFolder = Environment.getExternalStorageDirectory();




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title="Downloading";

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    private String details="Downloading is in progress";

    public Download(Activity activity,String folderLocation)
    {
        super(activity);
        setFolderLocation(folderLocation);
    }

    public String getFolderLocation() {
        if(folderLocation==null)
            folderLocation = activity.getPackageName()+"/data";
        return folderLocation;
    }
    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }

    public int getProgressBarType() {
        return progressBarType;
    }
    public void setProgressBarType(int type) {
        progressBarType=type;
    }
    public void clear()
    {
        if(progressDialogMap!=null) {
            for (Map.Entry<Integer, ProgressDialog> entry : progressDialogMap.entrySet()) {
                entry.getValue().dismiss();
                progressDialogMap.remove(entry.getKey());
            }
        }
        downloadFolder=null;
        aq=null;
        progressDialogMap=null;
        super.clear();
    }
    public void perform(String link,String location, boolean showProgress,final String success,final String fail,final DownloadResult downloadResult) {
        perform(link, location, showProgress, new DownloadResult() {
            public void success(File file) {
                if (downloadResult != null)
                    downloadResult.success(file);
                if (success != null)
                    new Toast(activity).perform(success);
            }

            public void fail(Object data) {
                if (downloadResult != null)
                    downloadResult.fail(data);
                if (fail != null)
                    new Toast(activity).perform(fail);
            }
        });
    }

    public void perform(String link,String location,Boolean showProgress, DownloadResult downloadResult)
    {
        String fileName =System.currentTimeMillis()+"";
        try
        {
            fileName = link.substring(link.lastIndexOf("/")+1, link.length());
            if(fileName.indexOf(".")==-1)
                fileName=fileName+"_"+System.currentTimeMillis()+"";
        }catch (Exception e)
        {
            fileName =System.currentTimeMillis()+"";
        }
        perform(link,location,showProgress, downloadResult,fileName);
    }
    public void perform(String link,String location,Boolean showProgress, DownloadResult downloadResult,String fileName)
    {
        perform(link,location,showProgress, downloadResult,fileName,null);
    }
    public void perform(String link,String location,final Boolean showProgress,final DownloadResult downloadResult,String fileName,File targetFolder)
    {
        if(aq==null)
            aq = new AQuery(activity);

        if(location==null || location.equals(""))
            location=getFolderLocation();

        if(location.startsWith("/"))
            location=location.substring(1);

        if(targetFolder==null)
            targetFolder = downloadFolder;

        File file = new File(targetFolder.getPath()+"/"+location+"/"+fileName);
        File target = new File(targetFolder, location+"/"+fileName);

        if (file.exists())
            downloadResult.success(file);
        else {

            int showProgressID=-1;
            if(showProgress)
                showProgressID=showNotificationProgress(getTitle(),getDetails());
            final int showProgressIDFinal=showProgressID;

            AjaxCallback callback=new AjaxCallback<File>() {
                public void callback(String url, File file, AjaxStatus status) {
                    if (showProgress && showProgressIDFinal!=-1)
                        hideNotificationProgress(showProgressIDFinal);

                    if (file != null)
                        downloadResult.success(file);
                    else
                        downloadResult.fail(status);
                }
            };

            aq.download(link, target,callback);
        }
    }

    public int showNotificationProgress(String title,String text)
    {
        int id =generateId();
        if(getProgressBarType()==PROGRESS_BAR_TYPE_NOTIFICATION) {
            NotificationManager notificationManager = (NotificationManager) activity.getApplicationContext().getSystemService(activity.getApplicationContext().NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity);
            mBuilder.setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(android.R.drawable.stat_sys_download)
                    .setLargeIcon(getActivityIcon(activity, activity.getPackageName()));

            mBuilder.setProgress(0, 0, true);
            mBuilder.setOngoing(true);
            notificationManager.notify(id, mBuilder.build());
        }
        else if(getProgressBarType()==PROGRESS_BAR_TYPE_POPUP)
        {
            ProgressDialog progressDialog= new ProgressDialog(activity);
            progressDialog.setTitle(title+"");
            progressDialog.setMessage(text + "");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialogMap.put(id,progressDialog);
        }
        return id;
    }
    public void hideNotificationProgress(int id)
    {
        if(getProgressBarType()==PROGRESS_BAR_TYPE_NOTIFICATION) {
            NotificationManager notificationManager = (NotificationManager) activity.getApplicationContext().getSystemService(activity.getApplicationContext().NOTIFICATION_SERVICE);
            notificationManager.cancel(id);
        }
        else if(getProgressBarType()==PROGRESS_BAR_TYPE_POPUP)
        {
            ProgressDialog  progressDialog =progressDialogMap.remove(id);
            if(progressDialog!=null)
                progressDialog.dismiss();
        }
    }
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static int generateId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
    public Bitmap getActivityIcon(Context context,String packageName) {

        try
        {
            Drawable drawable=context.getPackageManager().getApplicationIcon(packageName);
            Bitmap bitmap = null;

            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if(bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }

            if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }catch (Exception e)
        {

        }
        return null;
    }



    public static interface DownloadResult
    {
        void success(File file);
        void fail(Object file);
    }
}
