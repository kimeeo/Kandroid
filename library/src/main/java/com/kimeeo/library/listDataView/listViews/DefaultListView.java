package com.kimeeo.library.listDataView.listViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by bhavinpadhiyar on 1/20/16.
 */

abstract public class DefaultListView extends BaseListView implements IViewProvider
{
    abstract public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container);
    abstract public BaseItemHolder getItemHolder(int viewType,View view);
    abstract public int getTotalViewTypeCount();
    abstract public int getListItemViewType(int viewType,Object data);


    protected BaseListViewAdapter createListViewAdapter()
    {
        return new DefaultListViewAdapter(getDataManager(),this,this);
    }
}