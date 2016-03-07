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
    protected void openURL(String link) {
        new OpenBrowser(activity).perform(link);
    }
}
