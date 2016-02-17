/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kimeeo.library.listDataView.recyclerView.adapterLayout;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.BaseListDataView;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;

import java.util.List;


abstract public class BaseAdapterLayoutView extends BaseListDataView implements AdapterView.OnItemClickListener
{
    abstract protected BaseRecyclerViewAdapter createListViewAdapter();


    public View getRootView() {
        return mRootView;
    }

    protected View mRootView;
    protected View mEmptyView;
    protected ImageView mEmptyViewImage;
    protected TextView mEmptyViewMessage;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected BaseRecyclerViewAdapter mAdapter;

    protected ViewGroup mViewGroup;
    protected IAdapterLayoutView mAdapterLayout;

    protected void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        if(mAdapter!=null)
            mAdapter.garbageCollectorCall();

        mAdapter =null;
        mEmptyView =null;
        mViewGroup=null;
        if(mEmptyViewImage!=null)
            mEmptyViewImage.setImageBitmap(null);
        mEmptyViewImage=null;
        mEmptyViewMessage=null;
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


        mEmptyView= createEmptyView(mRootView);
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



    protected Drawable getEmptyViewDrawable()
    {
        Drawable drawable =getResources().getDrawable(R.drawable._empty_box);
        drawable.setColorFilter(getResources().getColor(R.color._emptyViewMessageColor), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }
    protected String getEmptyViewMessage()
    {
        return getResources().getString(R.string._emptyViewMessage);
    }
    public ImageView getEmptyImageView(View rootView)
    {
        return mEmptyViewImage;
    }
    public TextView getEmptyMessageView(View rootView)
    {
        return mEmptyViewMessage;
    }
    public View getEmptyView()
    {
        return mEmptyView;
    }



    protected View createEmptyView(View rootView)
    {
        View emptyView = rootView.findViewById(R.id.emptyView);

        if(rootView.findViewById(R.id.emptyViewImage)!=null && rootView.findViewById(R.id.emptyViewImage) instanceof ImageView) {
            mEmptyViewImage = (ImageView) rootView.findViewById(R.id.emptyViewImage);
            mEmptyViewImage.setImageDrawable(getEmptyViewDrawable());
        }

        if(rootView.findViewById(R.id.emptyViewMessage)!=null && rootView.findViewById(R.id.emptyViewMessage) instanceof TextView) {
            mEmptyViewMessage = (TextView) rootView.findViewById(R.id.emptyViewMessage);
            mEmptyViewMessage.setText(getEmptyViewMessage());
        }

        if(emptyView!=null)
            emptyView.setVisibility(View.GONE);
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
        if(mEmptyView!=null)
        {
            if(getDataManager().size()==0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }
        updateSwipeRefreshLayout(false);
    }
    public void onDataReceived(String url, Object value,Object status)
    {

    }
    public void onCallEnd(List<?> dataList,final boolean isRefreshData)
    {
        if(mEmptyView!=null)
        {
            if(getDataManager().size()==0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }

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
        if(mEmptyView!=null)
            mEmptyView.setVisibility(View.GONE);
    }

    public void onFirstCallEnd()
    {

    }
    public void onLastCallEnd()
    {

    }
}
