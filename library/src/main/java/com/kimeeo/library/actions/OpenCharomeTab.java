package com.kimeeo.library.actions;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.hitherejoe.tabby.CustomTabActivityHelper;
import com.hitherejoe.tabby.WebViewActivity;
import com.kimeeo.library.R;

import java.util.Map;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class OpenCharomeTab extends OpenInAppBrowser
{
    private Bitmap mCloseButtonBitmap;
    private CustomTabActivityHelper mCustomTabActivityHelper;
    private CustomTabsIntent.Builder intentBuilder;
    private int toolBarColor=-1;
    private int iconColor=-1;

    public String getOpenURL() {
        return openURL;
    }

    public void setOpenURL(String openURL) {
        this.openURL = openURL;
    }

    private String openURL;

    public OpenCharomeTab(Activity activity)
    {
        super(activity);
        setupCustomTabHelper(null, null, null, true);
    }
    public OpenCharomeTab(Activity activity,Class webView)
    {
        super(activity, webView);
        setupCustomTabHelper(null, null, null, true);
    }

    public OpenCharomeTab(Activity activity,String[] mayLaunchUrl,Map<String,PendingIntent> menus,Map<Bitmap,PendingIntent> buttons,boolean showClose) {
        super(activity);
        setupCustomTabHelper(mayLaunchUrl, menus, buttons, showClose);
    }

    public OpenCharomeTab(Activity activity,Class webView,String[] mayLaunchUrl,Map<String,PendingIntent> menus,Map<Bitmap,PendingIntent> buttons,boolean showClose) {
        super(activity, webView);
        setupCustomTabHelper(mayLaunchUrl, menus, buttons, showClose);
    }
    public void clear()
    {
        if(mCustomTabActivityHelper!=null)
            mCustomTabActivityHelper.unbindCustomTabsService(activity);
        mCustomTabActivityHelper=null;
        mCloseButtonBitmap=null;
        intentBuilder=null;
        customTabFallback=null;
        mConnectionCallback=null;
        super.clear();
    }

    public void setupCustomTabHelper(String[] mayLaunchUrl,Map<String,PendingIntent> menus,Map<Bitmap,PendingIntent> buttons,boolean showClose) {
        if(mCustomTabActivityHelper==null) {
            mCustomTabActivityHelper = new CustomTabActivityHelper();
            mCustomTabActivityHelper.setConnectionCallback(getConnectionCallback());

            mCustomTabActivityHelper.bindCustomTabsService(activity);
            intentBuilder = new CustomTabsIntent.Builder();


            int color = getToolBarColor();
            if(color==-1)
                color = activity.getResources().getColor(R.color.colorPrimary);

            intentBuilder.setToolbarColor(color);
            intentBuilder.setShowTitle(true);


            if(menus!=null)
            {
                for (Map.Entry<String, PendingIntent> entry : menus.entrySet()) {
                    intentBuilder.addMenuItem(entry.getKey(), entry.getValue());
                }
            }

            if(buttons!=null)
            {
                for (Map.Entry<Bitmap, PendingIntent> entry : buttons.entrySet()) {
                    intentBuilder.setActionButton(entry.getKey(), "", entry.getValue());
                }
            }

            Drawable closeIcon=null;
            if(showClose) {
                if(mCloseButtonBitmap==null)
                    closeIcon = activity.getResources().getDrawable(R.drawable._tabby_ic_arrow_back);
                else
                    intentBuilder.setCloseButtonIcon(mCloseButtonBitmap);
            }
            else
                closeIcon = activity.getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

            if(closeIcon!=null) {
                int iconColor = getIconColor();
                if (iconColor != -1)
                    closeIcon.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
                intentBuilder.setCloseButtonIcon(drawableToBitmap(closeIcon));
            }
            intentBuilder.setStartAnimations(activity,R.anim._tabby_slide_in_right, R.anim._tabby_slide_out_left);
            intentBuilder.setExitAnimations(activity, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        if(mayLaunchUrl!=null)
        {
            for (int i = 0; i < mayLaunchUrl.length; i++) {
                mCustomTabActivityHelper.mayLaunchUrl(Uri.parse(mayLaunchUrl[i]), null, null);
            }
        }
    }


    public void perform(String url) {
        try
        {
            openURL =url;
            //intentBuilder.addMenuItem("Share", createPendingShareIntent());
            mCustomTabActivityHelper.mayLaunchUrl(Uri.parse(url), null, null);
            CustomTabActivityHelper.openCustomTab(activity, intentBuilder.build(), Uri.parse(url), getCustomTabFallback());
        }
        catch (Exception e)
        {
            perform(url, null);
        }
    }

    private CustomTabActivityHelper.CustomTabFallback customTabFallback = new CustomTabActivityHelper.CustomTabFallback()
    {
        public void openUri(Activity activity, Uri uri)
        {
            perform(uri.toString(), null);
        }
    };
    public void setCustomTabFallback(CustomTabActivityHelper.CustomTabFallback customTabFallback) {
        this.customTabFallback = customTabFallback;
    }

    public CustomTabActivityHelper.CustomTabFallback getCustomTabFallback() {
        return customTabFallback;
    }
    public void setConnectionCallback(CustomTabActivityHelper.ConnectionCallback mConnectionCallback) {
        this.mConnectionCallback = mConnectionCallback;
    }

    public CustomTabActivityHelper.ConnectionCallback getConnectionCallback() {
        return mConnectionCallback;
    }

    protected void onChromTabsConnected() {

    }
    protected void onChromTabsDisconnected() {

    }

    public CustomTabActivityHelper.ConnectionCallback mConnectionCallback = new CustomTabActivityHelper.ConnectionCallback() {
        @Override
        public void onCustomTabsConnected() {
            onChromTabsConnected();
        }

        @Override
        public void onCustomTabsDisconnected() {
            onChromTabsDisconnected();
        }
    };


    private PendingIntent createPendingShareIntent() {
        Intent actionIntent = new Intent(Intent.ACTION_SEND);
        actionIntent.setType("text/plain");
        actionIntent.putExtra(Intent.EXTRA_TEXT, getOpenURL());
        actionIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Share URL With");
        PendingIntent intent=PendingIntent.getActivity(activity.getApplicationContext(), 0, actionIntent, 0);
        return intent;
    }

    public Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public int getToolBarColor() {
        return toolBarColor;
    }

    public void setToolBarColor(int toolBarColor) {
        this.toolBarColor = toolBarColor;
    }

    public int getIconColor() {
        return iconColor;
    }

    public void setIconColor(int iconColor) {
        this.iconColor = iconColor;
    }
}
