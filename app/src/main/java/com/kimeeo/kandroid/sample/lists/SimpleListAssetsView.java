package com.kimeeo.kandroid.sample.lists;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder2;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.deviceDataManager.AssetsDataManager;
import com.kimeeo.library.listDataView.dataManagers.deviceDataManager.FileDataManager;
import com.kimeeo.library.listDataView.dataManagers.deviceDataManager.RawDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.verticalViews.ListView;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
public class SimpleListAssetsView extends ListView
{
    // Data Manager
    protected DataManager createDataManager()
    {
        /*
        AssetsDataManager listData1= new AssetsDataManager(getActivity())
        {
            protected String getNextDataURL(PageData data)
            {
                if(data.curruntPage==1)
                    return "dir/asset_data.txt";
                return null;
            };
            public Class getLoadedDataParsingAwareClass()
            {
                return SampleDataParser.class;
            }
        };
        */
        /*
        RawDataManager listData1= new RawDataManager(getActivity())
        {
            protected String getNextDataURL(PageData data)
            {
                if(data.curruntPage==1)
                    return "asset_data";
                return null;
            };
            public Class getLoadedDataParsingAwareClass()
            {
                return SampleDataParser.class;
            }
        };
        */
        FileDataManager listData1= new FileDataManager(getActivity())
        {
            protected String getNextDataURL(PageData data)
            {
                if(data.curruntPage==1)
                    return Environment.getExternalStorageDirectory().toString()+"/kandroidData/assetData.txt";
                return null;
            };
            public Class getLoadedDataParsingAwareClass()
            {
                return SampleDataParser.class;
            }
        };

        listData1.setRefreshEnabled(false);
        return listData1;
    }

    protected BaseRecyclerViewAdapter createListViewAdapter()
    {
        return new DefaultRecyclerIndexableViewAdapter(getDataManager(),this);
    }

    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }

    //Return View Type here
    @Override
    public int getListItemViewType(int position,Object item)
    {
        if(position<4)
            return ViewTypes.VIEW_ITEM1;
        else
            return ViewTypes.VIEW_ITEM2;
    }
    // get View
    @Override
    public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return inflater.inflate(R.layout._sample_column_cell,null);
    }



    // get New BaseItemHolder
    @Override
    public BaseItemHolder getItemHolder(int viewType,View view)
    {
        if(viewType== ViewTypes.VIEW_ITEM1)
            return new RecyncleItemHolder1(view);
        else
            return new RecyncleItemHolder2(view);
    }



}
