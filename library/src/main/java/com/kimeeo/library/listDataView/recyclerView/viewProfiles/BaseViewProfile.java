package com.kimeeo.library.listDataView.recyclerView.viewProfiles;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseProfileRecyclerView;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.DefaultDividerDecoration;
import com.kimeeo.library.listDataView.recyclerView.IViewProvider;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class BaseViewProfile implements IViewProvider
{
    abstract public RecyclerView.LayoutManager createLayoutManager();
    abstract public BaseRecyclerViewAdapter createListViewAdapter();
    abstract public BaseItemHolder getItemHolder(int viewType,View view);

    public void configViewParam()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public BaseProfileRecyclerView getHost() {
        return host;
    }

    private BaseProfileRecyclerView host;
    public BaseViewProfile(String name, BaseProfileRecyclerView host)
    {
        this.name =name;
        this.host =host;
        configViewParam();
    }



    public void configViewAdapter(BaseRecyclerViewAdapter value)
    {

    }

    public RecyclerView.ItemDecoration createItemDecoration(Activity context)
    {
        return new DefaultDividerDecoration(context);
    }
    public RecyclerView.ItemAnimator createItemAnimator()
    {
        return  null;
    }
    public int getItemAnimatorDuration()
    {
        return -1;
    }
    //Confgi Your RecycleVIew Here
    public void configRecyclerView(RecyclerView mList,BaseRecyclerViewAdapter mAdapter)
    {

    }
    public void garbageCollectorCall() {
        host=null;
    }

    //Confgi Your Layout manager here
    public void  configLayoutManager(RecyclerView.LayoutManager layoutManager)
    {

    }
}