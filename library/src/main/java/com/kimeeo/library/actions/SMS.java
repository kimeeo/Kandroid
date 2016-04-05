package com.kimeeo.library.actions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.gun0912.tedpermission.PermissionListener;
import com.kimeeo.library.R;

import java.util.ArrayList;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class SMS extends BaseAction{

    public SMS(Activity activity) {
        super(activity);
    }

    public void perform(final String recipient,Boolean confirm) {
        perform(recipient, null, confirm);
    }
    public void perform(final String recipient,final String message,Boolean confirm) {
        perform(recipient,message,confirm,false);
    }
    public void perform(final String recipient,final String message,Boolean confirm,final Boolean sendWithFallback) {
        if(recipient!=null) {
            try {
                final SharedPreferences sp=activity.getSharedPreferences("SMS_SETTINGS", Context.MODE_PRIVATE);
                boolean doNotAskAgain =sp.getBoolean("doNotAskAgain", false);
                boolean allow =sp.getBoolean("allow", false);
                doNotAskAgain=false;
                if (confirm && doNotAskAgain==false)
                {
                    View checkBoxView = View.inflate(activity, R.layout._sms_don_not_ask_again, null);
                    CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            sp.edit().putBoolean("doNotAskAgain",isChecked).commit();
                        }
                    });

                    new AlertDialog.Builder(activity)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.mayCostSMSChargeTitle)
                            .setMessage(R.string.mayCostSMSCharge)
                            .setView(checkBoxView)
                            .setCancelable(false)
                            .setPositiveButton(R.string._yesClose, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sp.edit().putBoolean("allow",true).commit();
                                    performDirect(recipient, message,sendWithFallback);
                                }
                            })
                            .setNegativeButton(R.string._noClose, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    sp.edit().putBoolean("allow",false).commit();
                                    if(sendWithFallback)
                                        perform(recipient, message);
                                }
                            })
                            .show();
                }
                else if(doNotAskAgain && allow)
                    performDirect(recipient, message,sendWithFallback);
                else if(sendWithFallback)
                    perform(recipient, message);

            }catch (Exception e)
            {
                perform(recipient, message);
            }
        }
    }

    public String[] getPermissions() {
        return new String[]{Manifest.permission.SEND_SMS};
    }

    public void performDirect(final String recipient,final String message,final boolean sendWithFallback)
    {
        try
        {
            PermissionListener permissionListener = new PermissionListener()
            {
                @Override
                public void onPermissionGranted() {
                    permissionGranted(recipient, message,sendWithFallback);
                }
                @Override
                public void onPermissionDenied(ArrayList<String> arrayList) {
                    if(sendWithFallback)
                        perform(recipient, message);
                }
            };
            invokePermission(permissionListener);
        }catch (Exception e)
        {
            perform(recipient, message);
        }
    }

    protected void permissionGranted(String recipient, String message,boolean sendWithFallback) {
        try {
            if(recipient!=null && message!=null) {
                SmsManager manager = SmsManager.getDefault();
                PendingIntent sentIntent = PendingIntent.getActivity(activity, 0, new Intent(), 0);
                String msg = message;
                if(msg==null)
                    msg="";
                manager.sendTextMessage(recipient, null, msg, sentIntent, null);
            }
        }catch (Exception e)
        {
            if(sendWithFallback)
                perform(recipient, message);
        }
    }

    public void perform(String recipient,String message)
    {
        try
        {
            if(recipient!=null)
            {
                String uri = "smsto:" + recipient;
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
                intent.putExtra("compose_mode", true);
                if (message != null)
                    intent.putExtra("sms_body", message);
                activity.startActivity(intent);
            }
        }
        catch (Exception e)
        {

        }
    }
}
