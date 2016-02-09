package com.kimeeo.kandroid.sample.projectCore;

import com.kimeeo.kandroid.sample.lists.DefaultRecyclerIndexableViewAdapter;
import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.recyclerView.verticalViews.ListView;


/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
abstract public class DefaultVerticalListView extends ListView implements DefaultProjectDataManager.IDataManagerDelegate
{
     // Data Manager
    protected DataManager createDataManager()
    {
        DefaultProjectDataManager data=new DefaultProjectDataManager(getActivity(),this);
        return data;
    }
    protected BaseRecyclerViewAdapter createListViewAdapter()
    {
        return new DefaultRecyclerIndexableViewAdapter(getDataManager(),this);
    }
    abstract public String getNextDataURL(PageData data);
    abstract public Class<BaseDataParser> getLoadedDataParsingAwareClass();

    /*
    protected Drawable getEmptyViewDrawable()
    {
        int iconSize = getResources().getDimensionPixelSize(R.dimen._emptyViewMessageTextSize);
        return IconsUtils.getFontIconDrawable(getActivity(), FontAwesome.Icon.faw_dropbox, R.color._emptyViewMessageColor, iconSize);

    }
    protected String getEmptyViewMessage()
    {
        return getResources().getString(R.string._emptyViewMessage);
    }*/
}
