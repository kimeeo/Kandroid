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

    public boolean requiredPermission()
    {
        if(getPermissions()!=null && getPermissions().length!=0)
            return true;
        return false;
    }

    public boolean hasPermission()
    {
        boolean has=true;
        if(getPermissions()!=null && getPermissions().length!=0)
        {
            for (int i = 0; i < getPermissions().length; i++)
            {
                int result = ContextCompat.checkSelfPermission(activity, getPermissions()[i]);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    has = false;
                    break;
                }
            }
        }
        return has;
    }


    public void invokePermission(PermissionListener permissionListener)
    {
        if(requiredPermission())
        {
            if(hasPermission())
                permissionListener.onPermissionGranted();
            else {
                if(permissionsHelper==null)
                    permissionsHelper =new PermissionsHelper(activity);

                permissionsHelper.setOnPermission(permissionListener);
                permissionsHelper.check(getPermissions());
            }
        }
    }

    public String[] getPermissions() {
        return null;
    }

}
