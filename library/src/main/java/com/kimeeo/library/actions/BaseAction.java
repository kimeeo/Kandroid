package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.gun0912.tedpermission.PermissionListener;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class BaseAction {
    protected Activity activity;
    PermissionsHelper permissionsHelper;
    public BaseAction(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void clear()
    {
        activity=null;
        permissionsHelper=null;
    }


    public boolean requiredPermission(String[] permissions)
    {
        return permissions != null && permissions.length != 0;
    }
    public boolean requiredPermission()
    {
        return requiredPermission(requirePermissions());
    }

    public boolean hasPermission(String[] permissions)
    {
        boolean has=true;
        if(permissions!=null && permissions.length!=0)
        {
            for (int i = 0; i < permissions.length; i++)
            {
                int result = ContextCompat.checkSelfPermission(activity, permissions[i]);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    has = false;
                    break;
                }
            }
        }
        return has;
    }
    public boolean hasPermission()
    {
        return hasPermission(requirePermissions());
    }


    public void invokePermission(PermissionListener permissionListener)
    {
        invokePermission(requirePermissions(),getFriendlyPermissionsMeaning(), permissionListener);
    }

    public void invokePermission(String[] permissions,String[] friendlyPermissionsMeaning,PermissionListener permissionListener)
    {
        if(requiredPermission(permissions))
        {
            if(hasPermission(permissions))
                permissionListener.onPermissionGranted();
            else {
                if(permissionsHelper==null)
                    permissionsHelper =createPermissionsHelper();

                permissionsHelper.setOnPermission(permissionListener);
                permissionsHelper.check(permissions,friendlyPermissionsMeaning);
            }
        }
    }
    public String[] requirePermissions() {return null;}
    public String[] getFriendlyPermissionsMeaning() {return null;}

    protected PermissionsHelper createPermissionsHelper() {
        PermissionsHelper permissionsHelper = new PermissionsHelper(activity);
        return permissionsHelper;
    }
}
