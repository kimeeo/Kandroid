package com.kimeeo.kandroid.sample.lists;

import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/13/16.
 */
public class SampleDataParser extends BaseDataParser
{
    public Class getManagedObjectClass()
    {
        return SampleModel.class;
    }
    List<SampleModel> data;
    public ResultPageData page;
    public boolean parseRequired(){return false;}

    public int getActivePageIndex()
    {
        return page.active;
    }
    public int getTotalPageCount()
    {
        return page.total;
    }
    public int getPageSize(){return page.pageSize;}
    public String getNextPageURL()
    {
        return page.nextPageURL;
    }
    public String getRefreshPagePageURL()
    {
        return page.refreshPageURL;
    }
    public List<?> getList()
    {
        return data;
    }
    public Object getData()
    {
        return data;
    }


    public class ResultPageData{
        public int active;
        public int total;
        public int pageSize;
        public String nextPageURL;
        public String refreshPageURL;
    }
}


