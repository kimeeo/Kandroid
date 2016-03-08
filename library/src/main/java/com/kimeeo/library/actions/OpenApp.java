package com.kimeeo.library.actions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.kimeeo.library.R;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class OpenApp extends BaseAction{

    private String title;
    private String playStoreURL="https://play.google.com/store/apps/details?id=";

    public OpenApp(Activity activity) {
        super(activity);
    }

    public void perform(String classPath, boolean showFailMsg, String appName)
    {
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

    public void perform(String classPath)
    {
        if(classPath!=null) {
            try {
                Intent i = new Intent(Intent.ACTION_MAIN);
                PackageManager manager = activity.getPackageManager();
                i = manager.getLaunchIntentForPackage(classPath);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                activity.startActivity(i);
            } catch (Exception e)
            {

            }
        }
    }

    public void downloadApp(final String classPath,final String appName) {
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getTitle())
                .setMessage(appName +"("+classPath+") is not install on your device. Would you like to install it now?")
                .setPositiveButton(R.string._yesClose, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String link = getPlayStoreURL()+classPath;
                        openURL(link);
                    }
                })
                .setNegativeButton(R.string._noClose, null)
                .show();

    }
    protected void openURL(String link) {
        new OpenBrowser(activity).perform(link);
    }

    public String getTitle() {
        if(title==null)
            title="Download APP";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayStoreURL() {
        return playStoreURL;
    }

    public void setPlayStoreURL(String playStoreURL) {
        this.playStoreURL = playStoreURL;
    }
}
