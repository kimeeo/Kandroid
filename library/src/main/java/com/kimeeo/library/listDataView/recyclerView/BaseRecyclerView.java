/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kimeeo.library.listDataView.recyclerView;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.BaseListDataView;
import com.kimeeo.library.listDataView.EmptyViewHelper;

import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;


abstract public class BaseRecyclerView extends BaseListDataView implements AdapterView.OnItemClickListener
{
    protected RecyclerView recyclerView;
    protected View mRootView;
    protected BaseRecyclerViewAdapter mAdapter;
    protected EmptyViewHelper mEmptyViewHelper;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    abstract protected RecyclerView.LayoutManager createLayoutManager();

    abstract protected BaseRecyclerViewAdapter createListViewAdapter();

    protected RecyclerView.ItemAnimator createItemAnimator()
    {
        return  new FadeInAnimator();
    }

    public View getRootView() {
        return mRootView;
    }

    protected void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        if(mAdapter!=null)
            mAdapter.garbageCollectorCall();

        mAdapter =null;
        recyclerView = null;
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.clean();
        mEmptyViewHelper = null;
        mSwipeRefreshLayout=null;
    }
    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new DefaultDividerDecoration(getActivity());
    }
    protected RecyclerView getRecyclerView()
    {
        return recyclerView;
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

        recyclerView = createRecyclerView(mRootView);
        mEmptyViewHelper = createEmptyViewHelper();

        RecyclerView.LayoutManager layoutManager= createLayoutManager();
        configLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(createItemDecoration());

        mAdapter = createListViewAdapter();
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        configRecyclerView(recyclerView, mAdapter);
        setOnScrollListener(recyclerView);
        setItemAnimator(recyclerView);
        loadNext();
        onViewCreated(mRootView);
        return mRootView;
    }

    protected EmptyViewHelper createEmptyViewHelper() {
        return new EmptyViewHelper(getActivity(), createEmptyView(mRootView), this, true, true);
    }

    public void onViewCreated(View view) {

    }

    protected void setItemAnimator(RecyclerView mList)
    {
        RecyclerView.ItemAnimator itemAnimator = createItemAnimator();
        int itemAnimatorDuration = getItemAnimatorDuration();
        if(itemAnimator!=null) {
            itemAnimator.setAddDuration(itemAnimatorDuration);
            itemAnimator.setChangeDuration(itemAnimatorDuration);
            itemAnimator.setMoveDuration(itemAnimatorDuration);
            itemAnimator.setRemoveDuration(itemAnimatorDuration);
            mList.setItemAnimator(itemAnimator);
        }
    }

    protected int getItemAnimatorDuration()
    {
        return  200;
    }

    //Confgi Your RecycleVIew Here
    protected void configRecyclerView(RecyclerView mList,BaseRecyclerViewAdapter mAdapter)
    {

    }

    //Confgi Your Layout manager here
    protected void  configLayoutManager(RecyclerView.LayoutManager layoutManager)
    {

    }


    protected void setOnScrollListener(RecyclerView mList)
    {
        mList.setOnScrollListener(new EndlessRecyclerOnScrollListener(mList.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                if (getDataManager().canLoadNext())
                    loadNext();
            }

            public void onScroll(RecyclerView recyclerView, int dx, int dy) {
                onDataScroll(recyclerView, dx, dy);
            }
        });
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if(getDataManager().getRefreshEnabled())
            return inflater.inflate(R.layout._fragment_recycler_with_swipe_refresh_layout, container, false);
        else
            return inflater.inflate(R.layout._fragment_recycler, container, false);
    }
    protected RecyclerView createRecyclerView(View rootView)
    {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        return recyclerView;
    }




    protected View createEmptyView(View rootView)
    {
        View emptyView = rootView.findViewById(R.id.emptyView);
        return emptyView;
    }

    protected void onDataScroll(RecyclerView recyclerView, int dx, int dy)
    {

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
        if (isRefreshData)
            recyclerView.scrollToPosition(0);

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
