/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kimeeo.library.listDataView.recyclerView.adapterLayout;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.BaseListDataView;
import com.kimeeo.library.listDataView.EmptyViewHelper;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;

import java.util.List;


abstract public class BaseAdapterLayoutView extends BaseListDataView implements AdapterView.OnItemClickListener
{
    protected View mRootView;
    protected EmptyViewHelper mEmptyViewHelper;
    protected BaseRecyclerViewAdapter mAdapter;
    protected ViewGroup mViewGroup;
    protected IAdapterLayoutView mAdapterLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    abstract protected BaseRecyclerViewAdapter createListViewAdapter();

    public View getRootView() {
        return mRootView;
    }

    protected void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        if(mAdapter!=null)
            mAdapter.garbageCollectorCall();

        mAdapter =null;
        mViewGroup=null;


        mSwipeRefreshLayout=null;
    }
    protected ViewGroup getViewGroup()
    {
        return mViewGroup;
    }
    public BaseRecyclerViewAdapter getAdapter()
    {
        return mAdapter;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        configViewParam();
        mRootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        mViewGroup = createViewGroup(mRootView);


        mEmptyViewHelper = createEmptyViewHelper();
        mAdapter = createListViewAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.supportLoader=false;

        if(mViewGroup instanceof IAdapterLayoutView) {
            mAdapterLayout = (IAdapterLayoutView) mViewGroup;
            mAdapterLayout.setAdapter(mAdapter);
        }
        configViewGroup(mViewGroup, mAdapter);
        loadNext();
        onViewCreated(mRootView);
        return mRootView;
    }
    public void onViewCreated(View view) {

    }

    protected EmptyViewHelper createEmptyViewHelper() {
        return new EmptyViewHelper(getActivity(), createEmptyView(mRootView), this, true, true);
    }
    //Confgi Your RecycleVIew Here
    protected void configViewGroup(ViewGroup view,BaseRecyclerViewAdapter mAdapter)
    {

    }

    abstract protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState);
    abstract protected ViewGroup createViewGroup(View rootView);
    /*
    {
        if(getDataManager().getRefreshEnabled())
            return inflater.inflate(R.layout._fragment_recycler_with_swipe_refresh_layout, container, false);
        else
            return inflater.inflate(R.layout._fragment_recycler, container, false);
    }
    */
    protected View createEmptyView(View rootView)
    {
        View emptyView = rootView.findViewById(R.id.emptyView);
        return emptyView;
    }
    protected SwipeRefreshLayout getSwipeRefreshLayout()
    {
        return mSwipeRefreshLayout;
    }
    protected void configSwipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout = view;
        if(mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (getDataManager().canLoadRefresh())
                        loadRefreshData();
                    else
                        mSwipeRefreshLayout.setRefreshing(false);

                    mSwipeRefreshLayout.setEnabled(getDataManager().hasScopeOfRefresh());
                }
            });
            boolean refreshEnabled = getDataManager().getRefreshEnabled();
            mSwipeRefreshLayout.setEnabled(refreshEnabled);
            mSwipeRefreshLayout.setColorSchemeColors(R.array.progressColors);
        }
    }
    /*
    protected void loadRefreshData()
    {
        getDataManager().reset();
    }*/
    protected SwipeRefreshLayout createSwipeRefreshLayout(View rootView)
    {
        if(rootView.findViewById(R.id.swipeRefreshLayout)!=null) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            return swipeRefreshLayout;
        }
        return null;
    }
    public void updateSwipeRefreshLayout(boolean isRefreshData)
    {
        if(mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setRefreshing(false);

            if(isRefreshData)
                mSwipeRefreshLayout.setEnabled(getDataManager().hasScopeOfRefresh());
        }
    }
    public void onDataLoadError(String url, Object status)
    {
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updateView(getDataManager());
        updateSwipeRefreshLayout(false);
    }
    public void onDataReceived(String url, Object value,Object status)
    {

    }
    public void onCallEnd(List<?> dataList,final boolean isRefreshData)
    {
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updateView(getDataManager());
        updateSwipeRefreshLayout(isRefreshData);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Object baseObject = getDataManager().get(position);
        onItemClick(baseObject);
    }
    public void onItemClick(Object baseObject)
    {

    }


    public void onCallStart()
    {
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updatesStart();
    }

    public void onFirstCallEnd()
    {

    }
    public void onLastCallEnd()
    {

    }
}
