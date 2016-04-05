package com.kimeeo.library.listDataView.dataManagers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.kimeeo.library.R;
import com.kimeeo.library.actions.PermissionsHelper;

//import com.androidquery.callback.AjaxStatus;

/**
 * Created by bhavinpadhiyar on 12/23/15.
 */
abstract public class PermissionsBasedDataManager extends DataManager {

    private Context context;
    private PermissionsHelper permissionsHelper;

    public PermissionsBasedDataManager(Context context) {
        super(context);
        this.context = context;

    }

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
        permissionsHelper = null;
        context = null;
    }

    public boolean requiredPermission(String[] permissions) {
        return permissions != null && permissions.length != 0;
    }

    public boolean requiredPermission() {
        return requiredPermission(requirePermissions());
    }

    public boolean hasPermission(String[] permissions) {
        boolean has = true;
        if (permissions != null && permissions.length != 0) {
            for (int i = 0; i < permissions.length; i++) {
                int result = ContextCompat.checkSelfPermission(context, permissions[i]);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    has = false;
                    break;
                }
            }
        }
        return has;
    }

    public boolean hasPermission() {
        return hasPermission(requirePermissions());
    }


    public void invokePermission(PermissionListener permissionListener) {
        invokePermission(requirePermissions(), getFriendlyPermissionsMeaning(), permissionListener);
    }

    public void invokePermission(String[] permissions, String[] friendlyPermissionsMeaning, PermissionListener permissionListener) {
        if (requiredPermission(permissions)) {
            if (hasPermission(permissions))
                permissionListener.onPermissionGranted();
            else {
                if (permissionsHelper == null) {
                    permissionsHelper = createPermissionsHelper();

                }

                permissionsHelper.setOnPermission(permissionListener);
                permissionsHelper.check(permissions, friendlyPermissionsMeaning);
            }
        }
    }

    public String[] requirePermissions() {
        return null;
    }

    public String[] getFriendlyPermissionsMeaning() {
        return null;
    }

    protected PermissionsHelper createPermissionsHelper() {
        PermissionsHelper permissionsHelper = new PermissionsHelper(context);
        permissionsHelper.setShowRationaleConfirm(getShowRationaleConfirm());
        permissionsHelper.setRationaleConfirmText(getRationaleConfirmText());
        permissionsHelper.setRationaleMessage(getRationaleMessage());
        permissionsHelper.setShowDeniedMessage(getShowDeniedMessage());
        permissionsHelper.setDeniedCloseButtonText(getDeniedCloseButtonText());
        permissionsHelper.setDeniedMessage(getDeniedMessage());
        return permissionsHelper;
    }

    protected String getDeniedCloseButtonText() {
        return context.getString(R.string._permission_denied_close_button_text);
    }

    protected String getRationaleConfirmText() {
        return context.getString(R.string._permission_rationale_confirm_text);
    }

    protected String getRationaleMessage() {
        return context.getString(R.string._permission_rationale_message);
    }

    protected String getDeniedMessage() {
        return context.getString(R.string._permission_denied_message);
    }

    protected boolean getShowDeniedMessage() {
        return false;
    }

    protected boolean getShowRationaleConfirm() {
        return false;
    }
}
