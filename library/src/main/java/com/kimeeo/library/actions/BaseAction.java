package com.kimeeo.library.actions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class BaseAction {
    public Activity getActivity() {
        return activity;
    }

    protected Activity activity;
    PermissionsHelper permissionsHelper;
    public BaseAction(Activity activity) {
        this.activity = activity;
    }

    public void clear()
    {
        activity=null;
        permissionsHelper=null;
    }

    public boolean requiredPermission(String[] permissions)
    {
        if(permissions!=null && permissions.length!=0)
            return true;
        return false;
    }
    public boolean requiredPermission()
    {
        return requiredPermission(getPermissions());
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
        return hasPermission(getPermissions());
    }


    public void invokePermission(PermissionListener permissionListener)
    {
        invokePermission(getPermissions(), permissionListener);
    }

    public void invokePermission(String[] permissions,PermissionListener permissionListener)
    {
        if(requiredPermission(permissions))
        {
            if(hasPermission(permissions))
                permissionListener.onPermissionGranted();
            else {
                if(permissionsHelper==null)
                    permissionsHelper =createPermissionsHelper();

                permissionsHelper.setOnPermission(permissionListener);
                permissionsHelper.check(permissions);
            }
        }
    }
    public String[] getPermissions() {return null;}

    protected PermissionsHelper createPermissionsHelper() {
        PermissionsHelper permissionsHelper = new PermissionsHelper(activity);
        return permissionsHelper;
    }
}
