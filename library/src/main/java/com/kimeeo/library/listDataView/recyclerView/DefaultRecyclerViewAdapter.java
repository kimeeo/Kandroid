package com.kimeeo.library.listDataView.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;

/**
 * Created by bhavinpadhiyar on 1/12/16.
 */
public class DefaultRecyclerViewAdapter extends BaseRecyclerViewAdapter
{
    IViewProvider viewProvider;
    public void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        viewProvider=null;
    }

    public DefaultRecyclerViewAdapter(DataManager dataManager, OnCallService onCallService,IViewProvider viewProvider) {
        super(dataManager, onCallService);
        this.viewProvider = viewProvider;

    }
    public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return viewProvider.getItemView(viewType, inflater, container);
    }
    protected BaseItemHolder getItemHolder(int viewType,View view)
    {
        return  viewProvider.getItemHolder(viewType, view);
    }
    protected int getListItemViewType(int position,Object item)
    {
        return viewProvider.getListItemViewType(position, item);
    }
}