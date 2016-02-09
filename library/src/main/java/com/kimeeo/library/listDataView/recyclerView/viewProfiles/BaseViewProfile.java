package com.kimeeo.library.listDataView.recyclerView.viewProfiles;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.kimeeo.library.listDataView.recyclerView.BaseProfileRecyclerView;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.DefaultDividerDecoration;
import com.kimeeo.library.listDataView.recyclerView.IViewProvider;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class BaseViewProfile implements IViewProvider
{
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

    abstract public RecyclerView.LayoutManager createLayoutManager();
    abstract public BaseRecyclerViewAdapter createListViewAdapter();

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
