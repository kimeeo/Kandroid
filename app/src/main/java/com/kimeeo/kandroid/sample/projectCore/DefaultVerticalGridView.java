package com.kimeeo.kandroid.sample.projectCore;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.recyclerView.verticalViews.GridView;


/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
abstract public class DefaultVerticalGridView extends GridView implements DefaultProjectDataManager.IDataManagerDelegate
{
     // Data Manager
    protected DataManager createDataManager()
    {
        return new DefaultProjectDataManager(getActivity(),this);
    }
    abstract public String getNextDataURL(PageData data);
    abstract public Class<BaseDataParser> getLoadedDataParsingAwareClass();
}
