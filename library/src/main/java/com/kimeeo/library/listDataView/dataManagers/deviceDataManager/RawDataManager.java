package com.kimeeo.library.listDataView.dataManagers.deviceDataManager;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */
abstract public class RawDataManager extends BaseDataManager {

    public RawDataManager(Context context)
    {
        super(context);
    }

    protected InputStream getInputStream(Context context,String url) throws Exception
    {
        int id = context.getResources().getIdentifier(url, "raw", context.getPackageName());
        return context.getResources().openRawResource(id);
    }
}
