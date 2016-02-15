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
public class DirectoryDataManager extends DataManager {

    private static final String LOG_TAG= "BaseDataManager";

    private File directory;
    public void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        directory = null;
    }

    public boolean isFileList()
    {
        return true;
    }
    public DirectoryDataManager(Context context, File directory)
    {
        super(context);
        this.directory=directory;
    }
    public DirectoryDataManager(Context context, String path)
    {
        super(context);
        this.directory=new File(path);
    }
    protected void callService(String url)
    {
        boolean isRefreshPage = isRefreshPage(getPageData(), url);
        File file[]=null;
        if(directory!=null && directory.exists() && directory.isDirectory())
            file=directory.listFiles();

        if(file!=null)
        {
            if(isFileList()) {
                List<File> data1 = new ArrayList<>();
                for (int i = 0; i < file.length; i++) {
                    data1.add(file[i]);
                }
                dataHandler(url, data1, "DONE");
            }
            else
            {
                List<String> data1 = new ArrayList<>();
                for (int i = 0; i < file.length; i++) {
                    data1.add(file[i].getAbsolutePath());
                }
                dataHandler(url, data1, "DONE");
            }
        }
        else
        {
            dataHandler(url, null, "ERROR");
        }
    }
    protected String getNextDataURL(PageData data)
    {
        return data.curruntPage+"";
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
