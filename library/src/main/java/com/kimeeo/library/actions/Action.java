package com.kimeeo.library.actions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.Pair;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hitherejoe.tabby.WebViewActivity;
import com.hitherejoe.tabby.CustomTabActivityHelper;
import com.kimeeo.library.R;

import java.io.File;
import java.util.HashMap;
import android.support.v4.app.NotificationCompat.Builder;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bhavinpadhiyar on 2/2/16.
 */
public class Action {

    public static String ACTION_PHONE="phone";
    public static String ACTION_OPEN_APP="openApp";
    public static String ACTION_SMS="sms";
    public static String ACTION_SMS_DIRECT="smsDirect";
    public static String ACTION_OPEN_URL="openURL";
    public static String ACTION_OPEN_URL_IN_APP="openURLInApp";
    public static String ACTION_OPEN_URL_CHROME="openURLInChroame";
    public static String ACTION_MAIL_TO="mailto";

    public static String ACTION_SHOW_BUSY="showBusy";
    public static String ACTION_HIDE_BUSY="hideBusy";
    public static String ACTION_SHOW_MSG="showMsg";
    public static String ACTION_TEXT_SHARE="textShare";
    public static String ACTION_NAVIGATE_TO="navigaeTo";
    public static String ACTION_DOWNLOAD_FILE="downloadFile";
    public static String ACTION_DOWNLOAD_FILE_AND_OPEN="downloadFileAndOpen";
    public static String ACTION_IMG_SHARE="imgShare";
    public static String ACTION_IMG_SET="imgSet";
    public static String ACTION_COPY="copy";



    public static String ATTRIBUTE_URL="url";
    public static String ATTRIBUTE_TITLE="title";
    public static String ATTRIBUTE_DATA="data";

    public static String ATTRIBUTE_SUB_TITLE="subTitle";
    public static String ATTRIBUTE_NO="no";
    public static String ATTRIBUTE_BODY="body";
    public static String ATTRIBUTE_CLASS_PATH="classPath";
    public static String ATTRIBUTE_SHOW_FAIL="showFailMsg";
    public static String ATTRIBUTE_CONFIRM="confirm";

    public static String ATTRIBUTE_APP_NAME="appName";
    public static String ATTRIBUTE_MSG="msg";
    public static String ATTRIBUTE_DURATION="duration";


    public static String ATTRIBUTE_SHOW_PROGRESS="showProgress";
    public static String ATTRIBUTE_LONG="long";
    public static String ATTRIBUTE_SUCCESS_MSG="success";
    public static String ATTRIBUTE_FAIL_MSG="fail";

    public static String ATTRIBUTE_LATITUDE="latitude";
    public static String ATTRIBUTE_LONGITUDE="longitude";
    public static String ATTRIBUTE_ADDRESS="address";




    private Activity activity;
    private ProgressDialog progressDialog;
    private AQuery aq;
    private File downloadFolder = Environment.getExternalStorageDirectory();
    private Class webActivity;
    private Bitmap mCloseButtonBitmap;
    private CustomTabActivityHelper mCustomTabActivityHelper;
    private CustomTabsIntent.Builder intentBuilder;
    private Map<Integer,ProgressDialog> progressDialogMap=new HashMap<>();
    private boolean supportChromTab;


    public void clear()
    {
        mCustomTabActivityHelper.unbindCustomTabsService(activity);
        mCustomTabActivityHelper =null;
        intentBuilder=null;
        mCloseButtonBitmap=null;
        aq=null;
        downloadFolder=null;
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
            progressDialog=null;
        }
        if(progressDialogMap!=null) {
            for (Map.Entry<Integer, ProgressDialog> entry : progressDialogMap.entrySet()) {
                entry.getValue().dismiss();
                progressDialogMap.remove(entry.getKey());
            }
        }



    }



    private int progressBarType=PROGRESS_BAR_TYPE_NOTIFICATION;
    public static int PROGRESS_BAR_TYPE_NOTIFICATION=1;
    public static int PROGRESS_BAR_TYPE_POPUP=2;

    public void setDownloadFolder(File downloadFolder) {
        this.downloadFolder = downloadFolder;
    }


    public void setWebActivity(Class webActivity) {
        this.webActivity = webActivity;
    }

    private String folderLocation=null;
    public String getFolderLocation() {
        if(folderLocation==null)
            folderLocation = activity.getPackageName()+"/data";
        return folderLocation;
    }

    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }
    public Action(Activity activity, Class webActivity)
    {
        this.activity=activity;
        this.webActivity=webActivity;
    }
    public Action(Activity activity)
    {
        this.activity=activity;
    }



    public Action(Activity activity,boolean supportChromTab,String[] mayLaunchUrl,Map<String,PendingIntent> menus,Map<Bitmap,PendingIntent> buttons,boolean showClose)
    {
        this.activity=activity;
        this.supportChromTab =supportChromTab;
        if(supportChromTab)
            setupCustomTabHelper(mayLaunchUrl,menus,buttons,showClose);
    }
    public void setupCustomTabHelper(String[] mayLaunchUrl,Map<String,PendingIntent> menus,Map<Bitmap,PendingIntent> buttons,boolean showClose) {
        if(mCustomTabActivityHelper==null) {
            supportChromTab=true;
            mCustomTabActivityHelper = new CustomTabActivityHelper();
            mCustomTabActivityHelper.setConnectionCallback(mConnectionCallback);

            mCustomTabActivityHelper.bindCustomTabsService(activity);
            intentBuilder = new CustomTabsIntent.Builder();

            int color = activity.getResources().getColor(R.color.colorPrimary);
            intentBuilder.setToolbarColor(color);
            intentBuilder.setShowTitle(true);


            if(menus!=null)
            {
                for (Map.Entry<String, PendingIntent> entry : menus.entrySet()) {
                    intentBuilder.addMenuItem(entry.getKey(), entry.getValue());
                }
            }






            if(buttons!=null)
            {
                for (Map.Entry<Bitmap, PendingIntent> entry : buttons.entrySet()) {
                    intentBuilder.setActionButton(entry.getKey(),"", entry.getValue());
                }
            }




            if(showClose) {
                if(mCloseButtonBitmap==null) {
                    Drawable closeIcon = activity.getResources().getDrawable(R.drawable._tabby_ic_arrow_back);
                    mCloseButtonBitmap = drawableToBitmap(closeIcon);
                }
                intentBuilder.setCloseButtonIcon(mCloseButtonBitmap);
            }

            intentBuilder.setStartAnimations(activity,R.anim._tabby_slide_in_right, R.anim._tabby_slide_out_left);
            intentBuilder.setExitAnimations(activity, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        if(mayLaunchUrl!=null)
        {
            for (int i = 0; i < mayLaunchUrl.length; i++) {
                mCustomTabActivityHelper.mayLaunchUrl(Uri.parse(mayLaunchUrl[i]), null, null);
            }
        }
    }
    public Bitmap drawableToBitmap (Drawable drawable) {
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
    }



    private PendingIntent createPendingShareIntent() {
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("text/plain");
        actionIntent.putExtra(Intent.EXTRA_TEXT, "");
        actionIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"Share");
        PendingIntent intent=PendingIntent.getActivity(activity.getApplicationContext(), 0, actionIntent, 0);
        return intent;
    }

    public void openChromeTab(String url) {
        intentBuilder.addMenuItem("Share", createPendingShareIntent());
        CustomTabActivityHelper.openCustomTab(activity, intentBuilder.build(), Uri.parse(url), customTabFallback);
    }

    private CustomTabActivityHelper.CustomTabFallback customTabFallback = new CustomTabActivityHelper.CustomTabFallback()
    {
        public void openUri(Activity activity, Uri uri) {
            Intent intent = new Intent(activity, WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_URL, uri.toString());
            activity.startActivity(intent);
        }
    };

    private CustomTabActivityHelper.ConnectionCallback mConnectionCallback = new CustomTabActivityHelper.ConnectionCallback() {
        @Override
        public void onCustomTabsConnected() {

        }

        @Override
        public void onCustomTabsDisconnected() {

        }
    };



    public void phone(String phoneNo)
    {
        if(phoneNo!=null) {
            String link = "tel:" + phoneNo;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            activity.startActivity(browserIntent);
        }
    }
    public void sendSMSDirect(final String phoneNo,final String body,Boolean confirm)
    {
        if(phoneNo!=null) {
            try {
                if (confirm) {
                    new AlertDialog.Builder(activity)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.mayCostSMSChargeTitle)
                            .setMessage(R.string.mayCostSMSCharge)
                            .setPositiveButton(R.string._yesClose, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendSMSDirect(phoneNo, body);
                                }
                            })
                            .setNegativeButton(R.string._noClose, null)
                            .show();
                } else
                    sendSMSDirect(phoneNo, body);
            }catch (Exception e)
            {
                sendSMS(phoneNo, body);
            }

        }
    }
    public void sendSMSDirect(final String recipient,final String message)
    {
        try
        {
            if(recipient!=null && message!=null) {
                SmsManager manager = SmsManager.getDefault();
                PendingIntent sentIntent = PendingIntent.getActivity(activity, 0, new Intent(), 0);
                manager.sendTextMessage(recipient, null, message, sentIntent, null);
            }
        }catch (Exception e)
        {
            sendSMS(recipient,message);
        }

    }

    public void sendSMS(String phoneNo,String body)
    {
        if(phoneNo!=null) {
            String uri = "smsto:" + phoneNo;
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
            intent.putExtra("compose_mode", true);
            if (body != null)
                intent.putExtra("sms_body", body);
            activity.startActivity(intent);
        }
    }
    public void openURL(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        activity.startActivity(browserIntent);
    }
    public void openURLInAPP(String link, String title, String subTitle,Class webActivity) {
        if(link!=null && webActivity!=null) {
            Intent intent = new Intent(activity, webActivity);
            intent.putExtra(ATTRIBUTE_URL, link);

            if (title != null)
                intent.putExtra(ATTRIBUTE_TITLE, title);

            if (subTitle != null)
                intent.putExtra(ATTRIBUTE_SUB_TITLE, subTitle);


            activity.startActivity(intent);
        }
    }
    public void mailTo(String url) {

        String link=url;
        if(url.startsWith("mailto:")==false)
            link="mailto:"+url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        activity.startActivity(browserIntent);
    }

    public void openApp(String classPath, boolean showFailMsg, String appName) {

        if(classPath!=null) {
            try {
                Intent i = new Intent(Intent.ACTION_MAIN);
                PackageManager manager = activity.getPackageManager();
                i = manager.getLaunchIntentForPackage(classPath);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                activity.startActivity(i);
            } catch (Exception e) {
                if (showFailMsg)
                    downloadApp(classPath, appName);
            }
        }
    }
    public void showBusy(String msg, Integer duration) {
        hideBusy();

        progressDialog= new ProgressDialog(activity);
        progressDialog.setMessage(msg);
        progressDialog.show();

        if(duration!=-1)
        {
            final Handler handler = new Handler();
            final Runnable runnablelocal = new Runnable() {
                @Override
                public void run() {
                    if(progressDialog!=null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            };
            handler.postDelayed(runnablelocal,duration);
        }
    }

    public void hideBusy() {
        if(progressDialog!=null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
    public void showMSG(String msg) {
        if(msg!=null && msg.equals("")==false)
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }
    public void showMSG(String msg,boolean isLong) {
        if(msg!=null && msg.equals("")==false)
        {
            if(isLong)
                Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        }

    }
    public void textShare(String data, String title) {
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




    public void downloadFileAndOpen(String link,String location,final String title, boolean aTrue, final String success,final String fail,final FileOperation fileOperation) {
        downloadFile(link, location, aTrue, new FileOperation() {
            public void success(File file) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(Uri.fromFile(file), getMimeType(file.getPath()));
                activity.startActivity(Intent.createChooser(intent, title));

                if(fileOperation!=null)
                    fileOperation.success(file);
                if (success != null)
                    showMSG(success);
            }

            public void fail(Object data) {
                if(fileOperation!=null)
                    fileOperation.fail(data);
                if (fail != null)
                    showMSG(fail);
            }
        });
    }

    public void setImage(String link,String location,final String title, boolean aTrue, final String success,final String fail,final FileOperation fileOperation) {
        downloadFile(link, location, aTrue, new FileOperation() {
            public void success(File file) {
                Uri sendUri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                intent.setDataAndType(sendUri, "image/*");
                intent.putExtra("mimeType", "image/*");
                activity.startActivity(Intent.createChooser(intent, title));

                if(fileOperation!=null)
                    fileOperation.success(file);
                if (success != null)
                    showMSG(success);
            }

            public void fail(Object data) {
                if(fileOperation!=null)
                    fileOperation.fail(data);
                if (fail != null)
                    showMSG(fail);
            }
        });
    }
    public void shareImage(String link,String location,final String title, boolean aTrue, final String success,final String fail,final FileOperation fileOperation) {
        downloadFile(link, location, aTrue, new FileOperation() {
            public void success(File file) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getPath()));
                activity.startActivity(Intent.createChooser(shareIntent, title));

                if (fileOperation != null)
                    fileOperation.success(file);
                if (success != null)
                    showMSG(success);
            }

            public void fail(Object data) {
                if (fail != null)
                    showMSG(fail);

                if (fileOperation != null)
                    fileOperation.fail(data);
            }
        });
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


    public int showNotificationProgress(String title,String text)
    {
        int id =generateId();
        if(getProgressBarType()==PROGRESS_BAR_TYPE_NOTIFICATION) {
            NotificationManager notificationManager = (NotificationManager) activity.getApplicationContext().getSystemService(activity.getApplicationContext().NOTIFICATION_SERVICE);
            Builder mBuilder = new NotificationCompat.Builder(activity);
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
            ProgressDialog  progressDialog= new ProgressDialog(activity);
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


    public void downloadFile(String link,String location,final Boolean showProgress,final FileOperation fileOperation,String fileName,File targetFolder)
    {

        if(aq==null)
            aq = new AQuery(activity);

        if(location==null || location.equals(""))
            location=getFolderLocation();

        if(location.startsWith("/"))
            location=location.substring(1);


        File file = new File(targetFolder.getPath()+"/"+location+"/"+fileName);
        File target = new File(targetFolder, location+"/"+fileName);

        /*
        int showProgressID1=showNotificationProgress("Download","Download in progress");
        final int showProgressIDFinal1=showProgressID1;
        final Handler handler = new Handler();
        final Runnable runnablelocal = new Runnable() {
            @Override
            public void run() {
                hideNotificationProgress(showProgressIDFinal1);
            }
        };
        handler.postDelayed(runnablelocal,6000 );
*/
        if (file.exists())
            fileOperation.success(file);
        else {

            int showProgressID=-1;
            if(showProgress)
                showProgressID=showNotificationProgress("Downloading","Download in progress");
            final int showProgressIDFinal=showProgressID;

            AjaxCallback callback=new AjaxCallback<File>() {
                public void callback(String url, File file, AjaxStatus status) {
                    if (showProgress && showProgressIDFinal!=-1)
                        hideNotificationProgress(showProgressIDFinal);

                    if (file != null)
                        fileOperation.success(file);
                    else
                        fileOperation.fail(status);
                }
            };

            aq.download(link, target,callback);
        }
    }
    public void downloadFile(String link,String location,final Boolean showProgress,final FileOperation fileOperation,String fileName)
    {
        File ext = getDownloadFolder();
        downloadFile(link,location,showProgress,fileOperation,fileName,ext);
    }
    public void downloadFile(String link,String location,final Boolean showProgress,final FileOperation fileOperation)
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
        downloadFile(link,location,showProgress,fileOperation,fileName);

    }
    public void downloadFile(String link,String location, String title, boolean aTrue,final String success,final String fail,final FileOperation fileOperation) {
        downloadFile(link,location, aTrue, new FileOperation() {
            public void success(File file)
            {
                if(fileOperation!=null)
                    fileOperation.success(file);
                if(success!=null)
                    showMSG(success);
            }
            public void fail(Object data)
            {
                if(fileOperation!=null)
                    fileOperation.fail(data);
                if(fail!=null)
                    showMSG(fail);
            }
        });
    }


    public void copy(final String data, final String sucess)
    {
        if(data!=null) {
            Object clipboardobj = activity.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardobj instanceof android.text.ClipboardManager) {
                android.text.ClipboardManager clipboard1 = (android.text.ClipboardManager) clipboardobj;
                clipboard1.setText(data);
            } else {
                android.content.ClipboardManager clipboard2 = (android.content.ClipboardManager) clipboardobj;
                int currentVersion = android.os.Build.VERSION.SDK_INT;
                if (currentVersion >= 11)
                    clipboard2.setText(data);
            }
            if (sucess != null && sucess.equals("") == false)
                showMSG(sucess);
        }
    }
    public void navigateTo(long latitude,long longitude,String address) {
        String locationURL;
        if(latitude==0 || longitude==0)
            locationURL = "http://maps.google.com/maps?daddr="+latitude+","+longitude+"";
        else
            locationURL = "http://maps.google.com/maps?daddr="+address;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationURL));
        activity.startActivity(intent);
    }
    public void navigateTo(String latitude,String longitude,String address) {
        String locationURL;
        if(latitude!=null && longitude!=null)
            locationURL = "http://maps.google.com/maps?daddr="+latitude+","+longitude+"";
        else
            locationURL = "http://maps.google.com/maps?daddr="+address;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationURL));
        activity.startActivity(intent);
    }

    public void perform(String action)
    {
        String actionType = action.substring(0, action.indexOf("?"));
        actionType.toLowerCase();
        String valuesRaw=action.substring(action.indexOf("?")+1,action.length());
        String[] values = action.substring(action.indexOf("?")+1,action.length()).split("&");
        String key;
        String varValue;
        Map<String,String> actionMap= new HashMap<>();
        for (String entry:values) {
            String[] splitVal=entry.split("=");
            key=splitVal[0];
            varValue=splitVal[1];
            actionMap.put(key,varValue);
        }

        //val="openURLInApp?url=http://www.google.com&title=ABC&subTitle=dffs";
        //val="openURLInApp?url=http://www.google.com&title=ABC";
        //val="openURLInApp?url=http://www.google.com";


        //val="phone?no=8469492621";

        //val="sms?no=8469492621&body=FREE GIFT CLAIM";
        //val="sms?no=8469492621";

        //val="openURL?url=http://www.google.com";

        //val="mailto?apps@edspl.com?subject=App Feedback";



        if(actionType.equals(ACTION_PHONE.toLowerCase()))
        {
            phone(actionMap.get(ATTRIBUTE_NO));
        }
        else if(actionType.equals(ACTION_SMS_DIRECT.toLowerCase()))
        {
            final String no =actionMap.get(ATTRIBUTE_NO);
            final String body =actionMap.get(ATTRIBUTE_BODY);
            String confirm=actionMap.get(ATTRIBUTE_CONFIRM);
            if(confirm==null)
                confirm="true";

            if(confirm.equals("true"))
                sendSMSDirect(no, body, true);
            else
                sendSMSDirect(no, body, false);

        }
        else if(actionType.equals(ACTION_SMS.toLowerCase()))
        {
            String no =actionMap.get(ATTRIBUTE_NO);
            String body =actionMap.get(ATTRIBUTE_BODY);
            sendSMS(no,body);
        }
        else if(actionType.equals(ACTION_OPEN_URL.toLowerCase()))
        {
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;
            openURL(link);
        }
        else if(actionType.equals(ACTION_OPEN_URL_CHROME))
        {
            String link = actionMap.get(ATTRIBUTE_URL.toLowerCase());
            if(link.startsWith("http")==false)
                link ="http://"+link;

            setupCustomTabHelper(new String[]{link},null,null,true);

            openChromeTab(link);
        }
        else if(actionType.equals(ACTION_OPEN_URL_IN_APP))
        {
            String link = actionMap.get(ATTRIBUTE_URL.toLowerCase());
            if(link.startsWith("http")==false)
                link ="http://"+link;
            if(webActivity!=null) {
                String title = actionMap.get(ATTRIBUTE_TITLE);
                String subTitle = actionMap.get(ATTRIBUTE_SUB_TITLE);
                openURLInAPP(link, title, subTitle, webActivity);
            }
            else {
                setupCustomTabHelper(new String[]{link},null,null,true);
                openChromeTab(link);
            }
        }
        else if(actionType.equals(ACTION_MAIL_TO.toLowerCase()))
        {
            mailTo(valuesRaw);
        }
        else if(actionType.equals(ACTION_OPEN_APP.toLowerCase()))
        {
            String classPath=actionMap.get(ATTRIBUTE_CLASS_PATH);
            String showFailMsg=actionMap.get(ATTRIBUTE_SHOW_FAIL);
            if(showFailMsg==null)
                showFailMsg="false";

            String appName=actionMap.get(ATTRIBUTE_APP_NAME);
            if(appName==null)
                appName="Download APP";

            openApp(classPath,showFailMsg.equals("true"),appName);

        }
        else if(actionType.equals(ACTION_SHOW_BUSY.toLowerCase()))
        {
            String msg=actionMap.get(ATTRIBUTE_MSG);
            if(msg==null)
                msg = "";

            Integer duration=Integer.parseInt(actionMap.get(ATTRIBUTE_DURATION));
            if(duration==null)
                duration=-1;
            showBusy(msg,duration);
        }
        else if(actionType.equals(ACTION_HIDE_BUSY.toLowerCase()))
        {
            hideBusy();
        }
        else if(actionType.equals(ACTION_SHOW_MSG.toLowerCase()))
        {
            String msg=actionMap.get(ATTRIBUTE_MSG);
            String longDuration = actionMap.get(ATTRIBUTE_LONG);
            if(longDuration==null)
                longDuration="true";
            showMSG(msg,longDuration.equals("true"));
        }
        else if(actionType.equals(ACTION_TEXT_SHARE.toLowerCase()))
        {
            String title = actionMap.get(ATTRIBUTE_TITLE);
            String data = actionMap.get(ATTRIBUTE_DATA);
            textShare(data, title);
        }
        else if(action.equals(ACTION_DOWNLOAD_FILE.toLowerCase()))
        {
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;


            String title = actionMap.get(ATTRIBUTE_TITLE);
            String showProgress = actionMap.get(ATTRIBUTE_SHOW_PROGRESS);
            if(showProgress==null)
                showProgress="true";
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            final String fail = actionMap.get(ATTRIBUTE_FAIL_MSG);
            downloadFile(link,getFolderLocation(), title, showProgress.equals("true"), success, fail,null);
        }
        else if(action.equals(ACTION_IMG_SHARE))
        {
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;
            final String title = actionMap.get(ATTRIBUTE_TITLE);
            String showProgress = actionMap.get(ATTRIBUTE_SHOW_PROGRESS);
            if(showProgress==null)
                showProgress="true";
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            final String fail = actionMap.get(ATTRIBUTE_FAIL_MSG);

            shareImage(link,getFolderLocation(), title, showProgress.equals("true"), success, fail,null);
        }
        else if(action.equals(ACTION_IMG_SET))
        {
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;


            final String title = actionMap.get(ATTRIBUTE_TITLE);
            String showProgress = actionMap.get(ATTRIBUTE_SHOW_PROGRESS);
            if(showProgress==null)
                showProgress="true";
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            final String fail = actionMap.get(ATTRIBUTE_FAIL_MSG);
            setImage(link,getFolderLocation(), title, showProgress.equals("true"), success, fail,null);


        }
        else if(action.equals(ACTION_DOWNLOAD_FILE_AND_OPEN))
        {
            String link = actionMap.get(ATTRIBUTE_URL);
            if(link.startsWith("http")==false)
                link ="http://"+link;


            final String title = actionMap.get(ATTRIBUTE_TITLE);
            String showProgress = actionMap.get(ATTRIBUTE_SHOW_PROGRESS);
            if(showProgress==null)
                showProgress="true";
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            final String fail = actionMap.get(ATTRIBUTE_FAIL_MSG);
            downloadFileAndOpen(link,getFolderLocation(), title, showProgress.equals("true"), success, fail,null);
        }
        else if(action.equals(ACTION_COPY))
        {
            final String success = actionMap.get(ATTRIBUTE_SUCCESS_MSG);
            String data = actionMap.get(ATTRIBUTE_DATA);
            copy(data,success);
        }
        else if(action.equals(ACTION_NAVIGATE_TO))
        {
            String latitude= actionMap.get(ATTRIBUTE_LATITUDE);
            String longitude= actionMap.get(ATTRIBUTE_LONGITUDE);
            String address= actionMap.get(ATTRIBUTE_ADDRESS);
            navigateTo(latitude,longitude,address);
        }

        else
            perform(actionType,actionMap);
    }

    public void perform(String actionType,Map<String,String> actionMap)
    {

    }


    public void downloadApp(final String classPath,final String appName) {
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string._donloadApp)
                .setMessage(appName +"("+classPath+") is not install on your device. Would you like to install it now?")
                .setPositiveButton(R.string._yesClose, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String link = "https://play.google.com/store/apps/details?id="+classPath;
                        openURL(link);
                    }
                })
                .setNegativeButton(R.string._noClose, null)
                .show();

    }

    public File getDownloadFolder() {
        return downloadFolder;
    }


    public void launchActivity(Class target,boolean killCurrunt) {
        launchActivity(target,null,null,killCurrunt);
    }
    public void launchActivity(Class target) {
        launchActivity(target,null,null,false);
    }
    public void launchActivity(Class target,Pair<View, String>[] sharedElements,Map<String,Object> data,boolean killCurrunt) {
        launchActivity(activity,target,null,null,false);
    }
    public void launchActivity(Activity activity,Class target,Pair<View, String>[] sharedElements,Map<String,Object> data,boolean killCurrunt) {

        Intent intent = new Intent(activity,target);

        if(data!=null)
        {
            for (Map.Entry<String,Object> entry:data.entrySet())
            {
                if(entry.getValue() instanceof String)
                    intent.putExtra(entry.getKey(),(String)entry.getValue());
                if(entry.getValue() instanceof String[])
                    intent.putExtra(entry.getKey(),(String[])entry.getValue());

                else if(entry.getValue() instanceof Boolean)
                    intent.putExtra(entry.getKey(),(Boolean)entry.getValue());
                else if(entry.getValue() instanceof Boolean[])
                    intent.putExtra(entry.getKey(),(Boolean[])entry.getValue());

                else if(entry.getValue() instanceof Bundle)
                    intent.putExtra(entry.getKey(),(Bundle)entry.getValue());

                else if(entry.getValue() instanceof Byte)
                    intent.putExtra(entry.getKey(),(Byte)entry.getValue());
                else if(entry.getValue() instanceof Byte[])
                    intent.putExtra(entry.getKey(),(Byte[])entry.getValue());

                else if(entry.getValue() instanceof Double)
                    intent.putExtra(entry.getKey(),(Double)entry.getValue());
                else if(entry.getValue() instanceof Double[])
                    intent.putExtra(entry.getKey(),(Double[])entry.getValue());
                else if(entry.getValue() instanceof Float)
                    intent.putExtra(entry.getKey(),(Float)entry.getValue());
                else if(entry.getValue() instanceof Float[])
                    intent.putExtra(entry.getKey(),(Float[])entry.getValue());
                else if(entry.getValue() instanceof Intent)
                    intent.putExtra(entry.getKey(),(Intent)entry.getValue());
                else if(entry.getValue() instanceof Long)
                    intent.putExtra(entry.getKey(),(Long)entry.getValue());
                else if(entry.getValue() instanceof Long[])
                    intent.putExtra(entry.getKey(),(Long[])entry.getValue());
                else
                    intent.putExtra(entry.getKey(),entry.getValue().toString());
            }
        }

        if(Build.VERSION.SDK_INT >= 16 && sharedElements!=null && sharedElements.length!=0) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements);
            activity.startActivity(intent, options.toBundle());
        }
        else
            activity.startActivity(intent);

        if(killCurrunt)
            activity.finish();
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

    public int getProgressBarType() {
        return progressBarType;
    }
    public void setProgressBarType(int type) {
        progressBarType=type;
    }
    public static interface FileOperation
    {
        void success(File file);
        void fail(Object file);
    }
}
