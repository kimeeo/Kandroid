package com.kimeeo.kandroid.sample.viewPager;

import com.kimeeo.kandroid.sample.lists.SampleDataFragmentParser;
import com.kimeeo.kandroid.sample.projectCore.DefaultProjectDataManager;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.viewPager.fragmentPager.DefaultHorizontalFragmentViewPager;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
public class HFragmentPager extends DefaultHorizontalFragmentViewPager implements DefaultProjectDataManager.IDataManagerDelegate
{
    @Override
    protected DataManager createDataManager()
    {
        return new DefaultProjectDataManager(getActivity(),this);
    }
    //END URL
    public String getNextDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data"+pageData.curruntPage+".txt";
    }

    //Data Parser
    public Class getLoadedDataParsingAwareClass()
    {
        return SampleDataFragmentParser.class;
    }
}
