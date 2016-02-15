package com.kimeeo.library.listDataView.dataManagers.directoryListDataManager;

import android.content.Context;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class DirectoryDataManager extends DataManager {

    private static final String LOG_TAG= "BaseDataManager";

    public boolean isFileList()
    {
        return true;
    }
    public DirectoryDataManager(Context context)
    {
        super(context);
    }

    protected void callService(String path)
    {
        File directory =new File(path);
        boolean isRefreshPage = isRefreshPage(getPageData(), path);
        File file[]=null;
        if(directory!=null && directory.exists() && directory.isDirectory())
            file=directory.listFiles();

        if(file!=null && file.length!=0)
        {
            if(isRefreshPage)
                removeAll(this);

            if(isFileList()) {
                List<File> data1 = new ArrayList<>();
                for (int i = 0; i < file.length; i++) {
                    data1.add(file[i]);
                }
                dataHandler(path, data1, "DONE");
            }
            else
            {
                List<String> data1 = new ArrayList<>();
                for (int i = 0; i < file.length; i++) {
                    data1.add(file[i].getAbsolutePath());
                }
                dataHandler(path, data1, "DONE");
            }
        }
        else
        {
            dataHandler(path, null, "ERROR");
        }
    }
    protected void parseData(String url, Object value, Object status)
    {
        boolean isRefreshPage = isRefreshPage(getPageData(),url);
        if(isRefreshPage==false)
            updatePagingData();
        dataLoadingDone((List<?>)value,isRefreshPage);
    }
    protected void updatePagingData() {
        try
        {
            getPageData().curruntPage +=1;
            getPageData().totalPage +=1;
        }catch (Exception e)
        {
            getPageData().curruntPage=getPageData().totalPage=1;
        }
    }

    protected boolean isRefreshPage(PageData pageData,String url)
    {
        if(getRefreshDataURL(pageData)!=null)
            return getRefreshDataURL(pageData).equals(url);
        return false;
    }


    final public Class<BaseDataParser> getLoadedDataParsingAwareClass()
    {
        return null;
    }
}
