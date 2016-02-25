package com.kimeeo.kandroid.sample.projectCore;

import android.os.Handler;

import com.kimeeo.kandroid.sample.lists.DefaultRecyclerIndexableViewAdapter;
import com.kimeeo.kandroid.sample.model.SampleModel;
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
        final DefaultProjectDataManager data=new DefaultProjectDataManager(getActivity(),this);
        //data.add(getSample("OK","Works"));
        final Handler handler = new Handler();
        final Runnable runnablelocal = new Runnable() {
            @Override
            public void run() {
                if(getDataManager()!=null)
                    getDataManager().remove(1);
            }
        };
        handler.postDelayed(runnablelocal, 5000);

        return data;
    }
    private SampleModel getSample(String name, String phone) {
        SampleModel o = new SampleModel();
        o.name =name;
        o.details = phone;
        return o;
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
