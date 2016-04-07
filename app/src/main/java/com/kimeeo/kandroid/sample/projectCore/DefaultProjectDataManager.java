package com.kimeeo.kandroid.sample.projectCore;

import android.content.Context;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.aQuery.DefaultJSONDataManager;

/**
 * Created by bhavinpadhiyar on 1/11/16.
 */
public class DefaultProjectDataManager extends DefaultJSONDataManager
{
    private IDataManagerDelegate delegate;
    public DefaultProjectDataManager(Context context,IDataManagerDelegate delegate)
    {
        super(context);
        //setCachingTime(15 * 60 * 1000);
        setCachingTime(-1);
        this.delegate = delegate;
        setRefreshEnabled(true);
    }
    protected String getRefreshDataURL(PageData pageData){return delegate.getRefreshDataURL(pageData);}
    protected String getNextDataURL(PageData pageData)
    {
        return delegate.getNextDataURL(pageData);
    }
    public Class getLoadedDataParsingAwareClass()
    {
        return delegate.getLoadedDataParsingAwareClass();
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