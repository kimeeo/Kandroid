package com.kimeeo.library.listDataView.dataManagers;

import android.content.Context;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 2/17/16.
 */
public class StaticDataManger extends DataManager {

    private static final String LOG_TAG= "StaticDataManger";
    public StaticDataManger(Context context){
        super(context);
    }
    final protected void callService(String url){}
    final protected String getNextDataURL(PageData data){return null;}
    final protected void parseData(String url, Object value, Object status){}
    final public Class<BaseDataParser> getLoadedDataParsingAwareClass()
    {
        return null;
    }
}

