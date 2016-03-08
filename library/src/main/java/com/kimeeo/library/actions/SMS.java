package com.kimeeo.library.actions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import com.kimeeo.library.R;

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
        if(recipient!=null) {
            try {
                if (confirm)
                {
                    new AlertDialog.Builder(activity)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.mayCostSMSChargeTitle)
                            .setMessage(R.string.mayCostSMSCharge)
                            .setPositiveButton(R.string._yesClose, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    performDirect(recipient, message);
                                }
                            })
                            .setNegativeButton(R.string._noClose, null)
                            .show();
                }
                else
                    performDirect(recipient, message);
            }catch (Exception e)
            {
                perform(recipient, message);
            }
        }
    }
    public void perform(final String recipient,final String message,Boolean confirm,Boolean sendWithFallback) {
        if(recipient!=null) {
            try {
                if (confirm)
                {
                    new AlertDialog.Builder(activity)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.mayCostSMSChargeTitle)
                            .setMessage(R.string.mayCostSMSCharge)
                            .setPositiveButton(R.string._yesClose, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    performDirect(recipient, message);
                                }
                            })
                            .setNegativeButton(R.string._noClose, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    perform(recipient, message);
                                }
                            })
                            .show();
                }
                else
                    performDirect(recipient, message);

            }catch (Exception e)
            {
                perform(recipient, message);
            }
        }
    }

    public void performDirect(String recipient,String message)
    {
        try
        {
            if(recipient!=null && message!=null) {
                SmsManager manager = SmsManager.getDefault();
                PendingIntent sentIntent = PendingIntent.getActivity(activity, 0, new Intent(), 0);
                if(message==null)
                    message="";
                manager.sendTextMessage(recipient, null, message, sentIntent, null);
            }
        }catch (Exception e)
        {
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
