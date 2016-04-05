package com.kimeeo.library.actions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kimeeo.library.R;

import java.util.ArrayList;

/**
 * Created by BhavinPadhiyar on 05/04/16.
 */
public class PermissionsHelper {

    private final Context context;
    PermissionListener onPermission;
    TedPermission permission;
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            if (onPermission != null)
                onPermission.onPermissionGranted();
            permissionGranted();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            if (onPermission != null)
                onPermission.onPermissionDenied(deniedPermissions);
            permissionDenied(deniedPermissions);
        }
    };
    private String deniedCloseButtonText ="It\\'s ok, I don't want this";
    private String rationaleConfirmText="Give Permission";
    private String rationaleMessage="we need below permission to run the app smothly";
    private String deniedMessage="If you reject permission,you can not use this service\\n\\nPlease turn on permissions at [Setting] > [Permission]";
    private boolean showRationaleConfirm = true;
    private boolean showDeniedMessage = true;


    public PermissionsHelper(Context context)
    {
        this.context=context;
        deniedCloseButtonText =  context.getString(R.string._permission_denied_close_button_text);
        rationaleConfirmText=context.getString(R.string._permission_rationale_confirm_text);
        rationaleMessage =context.getString(R.string._permission_rationale_message);
        deniedMessage=    context.getString(R.string._permission_denied_message);
    }

    public PermissionListener getOnPermission() {
        return onPermission;
    }

    public PermissionsHelper setOnPermission(PermissionListener onPermission) {
        this.onPermission = onPermission;
        return this;
    }

    public void check(String[] permissions,String[] friendlyPermissionsMeaning)
    {
        if(friendlyPermissionsMeaning==null || friendlyPermissionsMeaning.length==0)
            friendlyPermissionsMeaning = permissions;

        if(permissions!=null && permissions.length!=0) {

            permission = new TedPermission(context);
            permission.setPermissionListener(permissionlistener);
            permission.setPermissions(permissions);

            if (isShowDeniedMessage()) {
                if (getDeniedCloseButtonText() != null)
                    permission.setDeniedCloseButtonText(getDeniedCloseButtonText());


                if (getDeniedMessage() != null) {
                    String msg = getDeniedMessage();
                    msg += "\n";

                    for (int i = 0; i < friendlyPermissionsMeaning.length; i++) {
                        String permissionVal = friendlyPermissionsMeaning[i];
                        if (permissionVal.lastIndexOf(".") != -1)
                            permissionVal = permissionVal.substring(permissionVal.lastIndexOf(".") + 1, permissionVal.length());

                        String s1 = permissionVal.substring(0, 1).toUpperCase();
                        permissionVal = s1 + permissionVal.substring(1).toLowerCase();
                        permissionVal = permissionVal.replaceAll("_", " ");

                        msg += "\n(" + (i + 1) + ")" + permissionVal;
                    }

                    permission.setDeniedMessage(msg);
                }
                permission.setGotoSettingButton(true);
            }

            if (isShowRationaleConfirm()) {
                if (getRationaleConfirmText() != null)
                    permission.setRationaleConfirmText(getRationaleConfirmText());


                if (getRationaleMessage() != null) {
                    String msg = getRationaleMessage();
                    msg += "\n";

                    for (int i = 0; i < friendlyPermissionsMeaning.length; i++) {
                        String permissionVal = friendlyPermissionsMeaning[i];

                        if (permissionVal.lastIndexOf(".") != -1)
                            permissionVal = permissionVal.substring(permissionVal.lastIndexOf(".") + 1, permissionVal.length());

                        if (permissionVal.lastIndexOf("_") != -1) {
                            String s1 = permissionVal.substring(0, 1).toUpperCase();
                            permissionVal = s1 + permissionVal.substring(1).toLowerCase();
                            permissionVal = permissionVal.replaceAll("_", " ");
                        }

                        msg += "\n(" + (i + 1) + ")" + permissionVal;
                    }
                    permission.setRationaleMessage(msg);
                }
            }



            permission.check();
        }
    }

    public void check(String[] permissions)
    {
        check(permissions,permissions);
    }

    public String getDeniedCloseButtonText() {
        return deniedCloseButtonText;
    }

    public PermissionsHelper setDeniedCloseButtonText(String val) {
        deniedCloseButtonText = val;
        return this;
    }

    public String getRationaleConfirmText() {
        return rationaleConfirmText;
    }

    public PermissionsHelper setRationaleConfirmText(String val) {
        rationaleConfirmText = val;
        return this;
    }

    public String getRationaleMessage() {
        return rationaleMessage;
    }

    public PermissionsHelper setRationaleMessage(String val) {
        rationaleMessage=val;
        return this;
    }

    public String getDeniedMessage() {
        return deniedMessage;
    }

    public PermissionsHelper setDeniedMessage(String val) {
        deniedMessage=val;
        return this;
    }

    protected void permissionGranted() {

    }

    protected void permissionDenied(ArrayList<String> deniedPermissions)
    {

    }

    public boolean requiredPermission(String[] permissions)
    {
        return permissions != null && permissions.length != 0;
    }
    public boolean hasPermission(String[] permissions)
    {
        boolean has=true;
        if(permissions!=null && permissions.length!=0)
        {
            for (int i = 0; i < permissions.length; i++)
            {
                int result = ContextCompat.checkSelfPermission(context, permissions[i]);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    has = false;
                    break;
                }
            }
        }
        return has;
    }

    public boolean isShowRationaleConfirm() {
        return showRationaleConfirm;
    }

    public void setShowRationaleConfirm(boolean showRationaleConfirm) {
        this.showRationaleConfirm = showRationaleConfirm;
    }

    public boolean isShowDeniedMessage() {
        return showDeniedMessage;
    }

    public void setShowDeniedMessage(boolean showDeniedMessage) {
        this.showDeniedMessage = showDeniedMessage;
    }
}
