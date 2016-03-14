package com.kimeeo.kandroid.sample.lists;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder2;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultProjectDataManager;
import com.kimeeo.library.actions.Action;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.adapterLayout.BaseLinearLayoutAdapterLayoutView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
public class AdaptorLayoutView extends BaseLinearLayoutAdapterLayoutView implements DefaultProjectDataManager.IDataManagerDelegate
{
    IListProvider listData=new IListProvider()
    {
        public List<?> getList(PageData data,Map<String, Object> param)
        {
            if(data.curruntPage==1) {
                List<SampleModel> list = new ArrayList<>();
                list.add(getSample("B1", "534534"));
                list.add(getSample("B2", "534534"));
                list.add(getSample("B3", "534534"));
                list.add(getSample("B4", "534534"));
                list.add(getSample("B5", "534534"));
                list.add(getSample("B6", "534534"));
                list.add(getSample("B7", "534534"));
                list.add(getSample("B8", "534534"));
                list.add(getSample("B9", "534534"));
                list.add(getSample("B10", "534534"));
                return list;
            }
            return null;
        }
    };
    Action action;

    protected DataManager createDataManager() {
        final ListDataManager listData1 = new ListDataManager(getActivity(), listData);
        listData1.setRefreshEnabled(false);

        final DefaultProjectDataManager data = new DefaultProjectDataManager(getActivity(), this);

        final Handler handler = new Handler();
        final Runnable runnablelocal = new Runnable() {
            @Override
            public void run() {
                getDataManager().add(0, getSample("ABC", "1"));
            }
        };
        handler.postDelayed(runnablelocal, 5000);

        return data;

        // return listData1;
    }

    private SampleModel getSample(String name, String phone) {
        SampleModel o = new SampleModel();
        o.name =name;
        o.details = phone;
        return o;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        super.onItemClick(parent,view,position,id);

    }

    public void onItemClick(Object baseObject)
    {
        super.onItemClick(baseObject);
        Toast.makeText(getActivity(), baseObject.toString(), Toast.LENGTH_SHORT).show();
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
        return inflater.inflate(R.layout._sample_column_cell_fix_width,null);
    }

    //END URL
    public String getNextDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data"+pageData.curruntPage+".txt";
    }

    public String getRefreshDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data_m1.txt";
    }

    //Data Parser
    public Class getLoadedDataParsingAwareClass()
    {
        return SampleDataParser.class;
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

    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }
}
