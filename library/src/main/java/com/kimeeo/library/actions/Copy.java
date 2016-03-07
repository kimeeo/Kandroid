package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class Copy extends BaseAction{

    public Copy(Activity activity) {
        super(activity);
    }

    public void perform(String data, String sucess) {
        try
        {
            if (data != null) {
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
                    new Toast(activity).perform(sucess);
            }
        }catch (Exception e)
        {

        }

    }

    public void perform(String data) {
        perform(data, null);
    }
}
