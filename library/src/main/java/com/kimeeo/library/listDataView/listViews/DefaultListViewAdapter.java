package com.kimeeo.library.listDataView.listViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
public class DefaultListViewAdapter extends BaseListViewAdapter
{
    private IViewProvider provider;

    public void garbageCollectorCall() {
        super.garbageCollectorCall();
        provider=null;
    }
    public DefaultListViewAdapter(DataManager dataManager, OnCallService listView,IViewProvider provider)
    {
        super(dataManager);
        setOnCallService(listView);
        this.provider =provider;
    }
    public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return provider.getItemView(viewType, inflater, container);
    }
    protected BaseItemHolder getItemHolder(int viewType,View view)
    {
        return  provider.getItemHolder(viewType, view);
    }
    protected int getListItemViewType(int position,Object item)
    {
        return provider.getListItemViewType(position, item);
    }
    protected int getTotalViewTypeCount()
    {
        return provider.getTotalViewTypeCount();
    }
}
