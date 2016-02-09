package com.kimeeo.kandroid.sample.projectCore;

import android.content.Context;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.aQuery.DefaultJSONDataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;

/**
 * Created by bhavinpadhiyar on 1/11/16.
 */
public class DefaultProjectDataManager extends DefaultJSONDataManager
{
    private IDataManagerDelegate host;
    public DefaultProjectDataManager(Context context,IDataManagerDelegate host)
    {
        super(context);
        setCachingTime(15 * 60 * 1000);
        //setCachingTime(-1);
        this.host = host;
        setRefreshEnabled(true);
    }
    protected String getRefreshDataURL(PageData pageData){return host.getRefreshDataURL(pageData);}
    protected String getNextDataURL(PageData pageData)
    {
        return host.getNextDataURL(pageData);
    }
    public Class getLoadedDataParsingAwareClass()
    {
        return host.getLoadedDataParsingAwareClass();
    }

    public static interface IDataManagerDelegate
    {
        String getNextDataURL(PageData pageData);
        String getRefreshDataURL(PageData pageData);
        Class getLoadedDataParsingAwareClass();
    }

    protected void updatePagingData(BaseDataParser loadedDataVO) {
        try
        {
            pageData.curruntPage +=1;
            pageData.totalPage +=1;
        }catch (Exception e)
        {
            pageData.curruntPage=pageData.totalPage=1;
        }

    }

}