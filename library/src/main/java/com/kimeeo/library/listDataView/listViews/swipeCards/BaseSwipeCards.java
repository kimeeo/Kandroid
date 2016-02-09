package com.kimeeo.library.listDataView.listViews.swipeCards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.listViews.BaseListView;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.rey.material.widget.ProgressView;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
abstract public class BaseSwipeCards extends BaseListView {

    private boolean firstItemIn = false;

    private ProgressView mProgressBar;
    private SwipeFlingAdapterView mFlingAdapterView;
    protected void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        mProgressBar=null;
        mFlingAdapterView=null;
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        if(getDataManager().getRefreshEnabled())
            return inflater.inflate(R.layout._fragment_swipe_card_view_with_swipe_refresh_layout, container, false);
        else
            return inflater.inflate(R.layout._fragment_swipe_card_view, container, false);
    }
    protected SwipeFlingAdapterView createFlingAdapterView(View rootView)
    {
        return (SwipeFlingAdapterView) rootView.findViewById(R.id.listView);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        configViewParam();
        getDataManager().setRefreshEnabled(false);
        mRootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        mFlingAdapterView = createFlingAdapterView(mRootView);
        mEmptyView= createEmptyView(mRootView);
        mAdapter = createListViewAdapter();
        mAdapter.supportLoader=false;



        mFlingAdapterView.setAdapter(mAdapter);




        configFlingView(mFlingAdapterView, mAdapter);


        if(mRootView.findViewById(R.id.progressBar)!=null)
            mProgressBar= (ProgressView)mRootView.findViewById(R.id.progressBar);


        mFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                getDataManager().remove(0);
                getAdapter().notifyDataSetChanged();
            }
            @Override
            public void onLeftCardExit(Object dataObject) {
                leftCardExit(dataObject);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                rightCardExit(dataObject);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter)
            {
                if(getDataManager().canLoadNext())
                    loadNext();
                getAdapter().notifyDataSetChanged();
            }
            public void onScroll(float var1)
            {

            }
        });
        mFlingAdapterView.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
               onItemClick(dataObject);
            }
        });

        loadNext();
        onViewCreated(mRootView);
        return mRootView;
    }
    public void leftCardExit(Object dataObject) {

    }


    public void rightCardExit(Object dataObject) {

    }
    public void selectRight() {
        mFlingAdapterView.getTopCardListener().selectRight();
    }
    public void selectLeft() {
        mFlingAdapterView.getTopCardListener().selectLeft();
    }
    protected void configFlingView(SwipeFlingAdapterView mList,BaseListViewAdapter mAdapter)
    {

    }

    public void onCallEnd(List<?> dataList,final boolean isRefreshData)
    {
        super.onCallEnd(dataList, isRefreshData);


        if(mProgressBar!=null)
            mProgressBar.setVisibility(View.GONE);
        firstItemIn = true;


    }

    public void onCallStart()
    {
        super.onCallStart();
        if(mProgressBar!=null && firstItemIn==false)
            mProgressBar.setVisibility(View.VISIBLE);
    }
}
