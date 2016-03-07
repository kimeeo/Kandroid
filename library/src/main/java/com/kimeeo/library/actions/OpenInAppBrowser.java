package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.hitherejoe.tabby.WebViewActivity;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class OpenInAppBrowser extends BaseAction{

    public OpenInAppBrowser(Activity activity) {
        super(activity);
    }


    public Class<?> getWebActivityClass() {
        return webViewClass;
    }

    public void setWebActivityClass(Class<?> webViewClass) {
        this.webViewClass = webViewClass;
    }

    private Class<?> webViewClass = WebViewActivity.class;


    public OpenInAppBrowser(Activity activity,Class webView)
    {
        super(activity);
        webViewClass = webView;
    }
    public void clear()
    {
        webViewClass =null;
        super.clear();
    }
    public void perform(String link, String title, String subTitle,Class webActivity) {
        if(link!=null && webActivity!=null)
        {
            try
            {
                Intent intent = new Intent(activity, webActivity);
                intent.putExtra(Action.ATTRIBUTE_URL, link);
                if (title != null)
                    intent.putExtra(Action.ATTRIBUTE_TITLE, title);

                if (subTitle != null)
                    intent.putExtra(Action.ATTRIBUTE_SUB_TITLE, subTitle);
                activity.startActivity(intent);
            }
            catch (Exception e)
            {
                new OpenBrowser(activity).perform(link);
            }
        }
    }
    public void perform(String link, String title, String subTitle) {
        perform(link, title, subTitle, getWebActivityClass());
    }
    public void perform(String link, String title) {
        perform(link, title, null,getWebActivityClass());
    }
    public void perform(String link) {
        perform(link, null, null, getWebActivityClass());
    }
}
