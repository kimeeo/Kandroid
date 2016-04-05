package com.kimeeo.library.actions;

import android.content.Context;
import android.support.annotation.StringRes;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kimeeo.library.R;

import java.util.ArrayList;

/**
 * Created by BhavinPadhiyar on 05/04/16.
 */
public class PermissionsHelper {

    private final Context context;


    private String deniedCloseButtonText ="It\\'s ok, I don't want this";
    private String rationaleConfirmText="Give Permission";
    private String rationaleMessage="we need below permission to run the app smothly";
    private String deniedMessage="If you reject permission,you can not use this service\\n\\nPlease turn on permissions at [Setting] > [Permission]";

    public PermissionListener getOnPermission() {
        return onPermission;
    }

    public PermissionsHelper setOnPermission(PermissionListener onPermission) {
        this.onPermission = onPermission;
        return this;
    }

    PermissionListener onPermission;


    public PermissionsHelper(Context context)
    {
        this.context=context;
        deniedCloseButtonText =  context.getString(R.string._permission_denied_close_button_text);
        rationaleConfirmText=context.getString(R.string._permission_rationale_confirm_text);
        rationaleMessage =context.getString(R.string._permission_rationale_message);
        deniedMessage=    context.getString(R.string._permission_denied_message);
    }
    TedPermission permission;

    public void check(String[] permissions)
    {
        if(permissions!=null && permissions.length!=0) {
            permission = new TedPermission(context);
            permission.setPermissionListener(permissionlistener);
            permission.setPermissions(permissions);

            if(getDeniedCloseButtonText()!=null)
                permission.setDeniedCloseButtonText(getDeniedCloseButtonText());

            if(getRationaleConfirmText()!=null)
                permission.setRationaleConfirmText(getRationaleConfirmText());

            if(getRationaleMessage()!=null) {
                String msg=getRationaleMessage();
                msg +="\n";

                for (int i = 0; i < permissions.length; i++) {
                    String permissionVal=permissions[i];
                    if(permissionVal.lastIndexOf(".")!=-1)
                        permissionVal = permissionVal.substring(permissionVal.lastIndexOf(".")+1,permissionVal.length());

                    if(permissions.length==1)
                        msg +="\n"+permissionVal;
                    else
                        msg +="\n("+(i+1)+")"+permissionVal;
                }
                permission.setRationaleMessage(msg);
            }

            if(getDeniedMessage()!=null)
                permission.setDeniedMessage(getDeniedMessage());

            permission.setGotoSettingButton(true);
            permission.check();
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            if(onPermission!=null)
                onPermission.onPermissionGranted();
            permissionGranted();
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions)
        {
            if(onPermission!=null)
                onPermission.onPermissionDenied(deniedPermissions);
            permissionDenied(deniedPermissions);
        }
    };

    public String getDeniedCloseButtonText() {
        return deniedCloseButtonText;
    }
    public String getRationaleConfirmText() {
        return rationaleConfirmText;
    }
    public String getRationaleMessage() {
        return rationaleMessage;
    }
    public String getDeniedMessage() {
        return deniedMessage;
    }
    public PermissionsHelper setDeniedCloseButtonText(String val) {
        deniedCloseButtonText=val;
        return this;
    }
    public PermissionsHelper setRationaleConfirmText(String val) {
        rationaleConfirmText=val;
        return this;
    }
    public PermissionsHelper setRationaleMessage(String val) {
        rationaleMessage=val;
        return this;
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
}
