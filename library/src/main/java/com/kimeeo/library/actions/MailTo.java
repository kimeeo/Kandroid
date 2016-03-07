package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class MailTo extends BaseAction{

    public MailTo(Activity activity) {
        super(activity);
    }

    public void perform(String url) {

        String link=url;
        if(url.startsWith("mailto:")==false)
            link="mailto:"+url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        activity.startActivity(browserIntent);
    }
}
