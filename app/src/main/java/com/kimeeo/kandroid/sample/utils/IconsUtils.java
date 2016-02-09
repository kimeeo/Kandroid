package com.kimeeo.kandroid.sample.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.io.Closeable;
import java.io.IOException;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.applyDimension;

/**
 * Created by bhavinpadhiyar on 7/15/15.
 */
public class IconsUtils
{
    public static void applyToMenuItem(Activity activity,MenuItem menuItem, IIcon icon,int colorRes)
    {
        int color = activity.getResources().getColor(colorRes);
        Drawable iconDrawable = new IconicsDrawable(activity,icon).color(color).actionBar();
        menuItem.setIcon(iconDrawable);
    }
    public static Drawable getFontIconDrawable(Activity activity,IIcon icon,int colorRes,int size)
    {
        int color = activity.getResources().getColor(colorRes);
        Drawable iconDrawable = new IconicsDrawable(activity,icon).color(color).sizeDp(size);
        return iconDrawable;
    }
    public static String getFormattedName(IIcon icon)
    {
        return icon.getFormattedName().replaceFirst("_", "-");
    }

    public static String getFormattedName(String icon)
    {
        return icon.replaceFirst("_", "-");
    }



    static int convertDpToPx(Context context, float dp) {
        return (int) applyDimension(COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    static boolean isEnabled(int[] stateSet) {
        for (int state : stateSet)
            if (state == android.R.attr.state_enabled)
                return true;
        return false;
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Don't care
            }
        }
    }


    public static void applyToButtonLeft(Activity activity,Button button, IIcon icon,int colorRes,int size)
    {
        Drawable iconDrawable = getFontIconDrawable(activity,icon,colorRes,size);
        button.setCompoundDrawables(iconDrawable,null,null,null);
    }
    public static void applyToButtonRight(Activity activity,Button button, IIcon icon,int colorRes,int size)
    {
        Drawable iconDrawable = getFontIconDrawable(activity, icon, colorRes, size);
        button.setCompoundDrawables(null,null,iconDrawable,null);
    }
    public static void applyToButtonTop(Activity activity,Button button, IIcon icon,int colorRes,int size)
    {
        Drawable iconDrawable = getFontIconDrawable(activity, icon, colorRes, size);
        button.setCompoundDrawables(null,iconDrawable,null,null);
    }
    public static void applyToButtonBottom(Activity activity,Button button, IIcon icon,int colorRes,int size)
    {
        Drawable iconDrawable = getFontIconDrawable(activity, icon, colorRes, size);
        button.setCompoundDrawables(null,null,null,iconDrawable);
    }
    public static void applyToImageButton(Activity activity,ImageButton button, IIcon icon,int colorRes,int size)
    {
        Drawable iconDrawable = getFontIconDrawable(activity, icon, colorRes, size);
        button.setImageDrawable(iconDrawable);
    }
}
