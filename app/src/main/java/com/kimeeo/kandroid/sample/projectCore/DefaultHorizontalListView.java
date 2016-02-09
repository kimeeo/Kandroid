package com.kimeeo.kandroid.sample.projectCore;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.recyclerView.horizontalViews.ListView;

/**
 * Created by bhavinpadhiyar on 1/12/16.
 */
abstract public class DefaultHorizontalListView extends ListView implements DefaultProjectDataManager.IDataManagerDelegate
{
    // Data Manager
    protected DataManager createDataManager()
    {
        return new DefaultProjectDataManager(getActivity(),this);
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