package com.kimeeo.kandroid.sample.lists;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.directoryListDataManager.DirectoryDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.verticalViews.ListView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
public class SimpleListDirectoryView extends ListView
{
    // Data Manager
    protected DataManager createDataManager()
    {
        DirectoryDataManager listData1= new DirectoryDataManager(getActivity())
        {
            protected String getNextDataURL(PageData data)
            {
                if(data.curruntPage==1)
                    return Environment.getExternalStorageDirectory().toString();
                return null;
            };
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
            return new VlistItemHolder1(view);
        else
            return new VlistItemHolder2(view);
    }
    // Update View Here
    public class VlistItemHolder1 extends BaseItemHolder {

        @Bind(R.id.label)TextView label;
        @Bind(R.id.backgroud)ImageView image;
        public VlistItemHolder1(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void updateItemView(Object item,View view,int position)
        {
            File listObject = (File)item;
            label.setText(position + " -> " + listObject.getAbsolutePath());

        }
    }



    // Update View Here
    public class VlistItemHolder2 extends BaseItemHolder {

        @Bind(R.id.label)TextView label;
        @Bind(R.id.backgroud)ImageView image;
        public VlistItemHolder2(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void updateItemView(Object item,View view,int position)
        {
            File listObject = (File)item;
            label.setText(position + " -> " + listObject.getAbsolutePath());
        }
    }
}
