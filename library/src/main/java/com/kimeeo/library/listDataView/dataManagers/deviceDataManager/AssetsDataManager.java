package com.kimeeo.library.listDataView.dataManagers.deviceDataManager;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by bhavinpadhiyar on 2/15/16.
 */
abstract public class AssetsDataManager extends BaseDataManager {

    public AssetsDataManager(Context context)
    {
        super(context);
    }

    @Override
    public String[] requirePermissions() {
        return null;
    }
    protected InputStream getInputStream(Context context,String url) throws Exception
    {
        return context.getAssets().open(url);
    }
}
