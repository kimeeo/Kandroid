package com.kimeeo.library.listDataView.listViews.swipeDeck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.listViews.BaseItemHolder;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.kimeeo.library.listDataView.listViews.DefaultListViewAdapter;
import com.kimeeo.library.listDataView.listViews.IViewProvider;
import com.rey.material.widget.ProgressView;

/**
 * Created by bhavinpadhiyar on 2/4/16.
 */
abstract public class DefaultSwipeDeck extends BaseSwipeDeck implements IViewProvider {
    abstract public BaseItemHolder getItemHolder(int viewType,View view);
    abstract public int getTotalViewTypeCount();
    abstract public int getListItemViewType(int viewType,Object data);
    abstract public int getItemViewRes(int viewType,LayoutInflater inflater,ViewGroup container);
    public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return inflater.inflate(getItemViewRes(viewType,inflater,container),container,false);
    }

    protected BaseListViewAdapter createListViewAdapter()
    {
        return new DefaultListViewAdapter(getDataManager(),this,this);
    }
}
