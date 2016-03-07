package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class LaunchActivity extends BaseAction{

    public LaunchActivity(Activity activity) {
        super(activity);
    }

    public void perform(Activity  sourceActivity,Class target,Pair<View, String>[] sharedElements,Map<String,Object> data,boolean killCurrunt) {
        try
        {
            Intent intent = new Intent(activity,target);

            if(data!=null)
            {
                for (Map.Entry<String,Object> entry:data.entrySet())
                {
                    if(entry.getValue()!=null) {
                        if (entry.getValue() instanceof String)
                            intent.putExtra(entry.getKey(), (String) entry.getValue());
                        else if (entry.getValue() instanceof String[])
                            intent.putExtra(entry.getKey(), (String[]) entry.getValue());
                        else if (entry.getValue() instanceof Integer)
                            intent.putExtra(entry.getKey(), (Integer) entry.getValue());
                        else if (entry.getValue() instanceof Integer[])
                            intent.putExtra(entry.getKey(), (Integer[]) entry.getValue());
                        else if (entry.getValue() instanceof Boolean)
                            intent.putExtra(entry.getKey(), (Boolean) entry.getValue());
                        else if (entry.getValue() instanceof Boolean[])
                            intent.putExtra(entry.getKey(), (Boolean[]) entry.getValue());
                        else if (entry.getValue() instanceof Bundle)
                            intent.putExtra(entry.getKey(), (Bundle) entry.getValue());
                        else if (entry.getValue() instanceof Byte)
                            intent.putExtra(entry.getKey(), (Byte) entry.getValue());
                        else if (entry.getValue() instanceof Byte[])
                            intent.putExtra(entry.getKey(), (Byte[]) entry.getValue());
                        else if (entry.getValue() instanceof Character)
                            intent.putExtra(entry.getKey(), (Character) entry.getValue());
                        else if (entry.getValue() instanceof Character[])
                            intent.putExtra(entry.getKey(), (Character[]) entry.getValue());
                        else if (entry.getValue() instanceof CharSequence)
                            intent.putExtra(entry.getKey(), (CharSequence) entry.getValue());
                        else if (entry.getValue() instanceof CharSequence[])
                            intent.putExtra(entry.getKey(), (CharSequence[]) entry.getValue());
                        else if (entry.getValue() instanceof Serializable)
                            intent.putExtra(entry.getKey(), (Serializable) entry.getValue());
                        else if (entry.getValue() instanceof Short)
                            intent.putExtra(entry.getKey(), (Short) entry.getValue());
                        else if (entry.getValue() instanceof Short[])
                            intent.putExtra(entry.getKey(), (Short[]) entry.getValue());
                        else if (entry.getValue() instanceof Double)
                            intent.putExtra(entry.getKey(), (Double) entry.getValue());
                        else if (entry.getValue() instanceof Double[])
                            intent.putExtra(entry.getKey(), (Double[]) entry.getValue());
                        else if (entry.getValue() instanceof Float)
                            intent.putExtra(entry.getKey(), (Float) entry.getValue());
                        else if (entry.getValue() instanceof Float[])
                            intent.putExtra(entry.getKey(), (Float[]) entry.getValue());
                        else if (entry.getValue() instanceof Intent)
                            intent.putExtra(entry.getKey(), (Intent) entry.getValue());
                        else if (entry.getValue() instanceof Long)
                            intent.putExtra(entry.getKey(), (Long) entry.getValue());
                        else if (entry.getValue() instanceof Long[])
                            intent.putExtra(entry.getKey(), (Long[]) entry.getValue());
                        else if (entry.getValue() instanceof Parcelable[])
                            intent.putExtra(entry.getKey(), (Parcelable[]) entry.getValue());
                        else if (entry.getValue() instanceof Object[])
                            intent.putExtra(entry.getKey(), (Object[]) entry.getValue());
                        else if (entry.getValue() instanceof Object[])
                            intent.putExtra(entry.getKey(), (Object[]) entry.getValue());
                        else if (entry.getValue() instanceof Intent)
                            intent.putExtras((Intent)entry.getValue());
                        else
                            intent.putExtra(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
            if(Build.VERSION.SDK_INT >= 16 && sharedElements!=null && sharedElements.length!=0) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements);
                sourceActivity.startActivity(intent, options.toBundle());
            }
            else
                sourceActivity.startActivity(intent);

            if(killCurrunt)
                sourceActivity.finish();
        }
        catch (Exception e)
        {

        }
    }

    public void perform(Class target,boolean killCurrunt) {
        perform(activity,target, null, null, killCurrunt);
    }
    public void perform(Class target) {
        perform(activity,target, null,null,false);
    }
    public void perform(Class target,Pair<View, String>[] sharedElements,Map<String,Object> data,boolean killCurrunt) {
        perform(activity,target, sharedElements,data,killCurrunt);
    }
}
