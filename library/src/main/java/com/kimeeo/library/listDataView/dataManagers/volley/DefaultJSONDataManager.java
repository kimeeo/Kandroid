package com.kimeeo.library.listDataView.dataManagers.volley;

import android.content.Context;

/**
 * Created by bhavinpadhiyar on 12/23/15.
 */


abstract public class DefaultJSONDataManager extends JSONDataManager {
    private static final String LOG_TAG= "DefaultJSONDataManager";
    public DefaultJSONDataManager(Context context,IVolleyRequestProvider volleyRequestController)
    {
        super(context,volleyRequestController);
    }
}
