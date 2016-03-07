package com.kimeeo.library.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by bhavinpadhiyar on 3/7/16.
 */
public class OpenBrowser extends BaseAction{

    public OpenBrowser(Activity activity) {
        super(activity);
    }

    public void perform(String link) {
        if(link.startsWith("http")==false)
            link ="http://"+link;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        activity.startActivity(browserIntent);
    }
}
