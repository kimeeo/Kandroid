package com.kimeeo.library.listDataView.listViews.carouselView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.listViews.BaseListView;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.rey.material.widget.ProgressView;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/22/16.
 */
abstract public class BaseCarouselView extends BaseListView
{

    protected void garbageCollectorCall() {
        super.garbageCollectorCall();
        mRootView=null;
        mEmptyView=null;
        if(mAdapter!=null)
            mAdapter.garbageCollectorCall();
        mAdapter=null;
        mCoverFlow=null;
        mEmptyViewImage= null;
        mEmptyViewMessage= null;
    }



    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView;
        if(getDataManager().getRefreshEnabled())
            rootView= inflater.inflate(R.layout._fragment_cover_flow_page_view_with_swipe_refresh_layout, container, false);
        else
            rootView= inflater.inflate(R.layout._fragment_cover_flow_page_view, container, false);
        return rootView;
    }

    public CoverFlowCarousel getCoverFlow() {
        return mCoverFlow;
    }

    private CoverFlowCarousel mCoverFlow;
    private ProgressView mProgressBar;
    private boolean firstItemIn = false;
    private boolean firstDataIn = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        configViewParam();
        getDataManager().setRefreshEnabled(false);
        mRootView = createRootView(inflater, container, savedInstanceState);

        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        mCoverFlow = createCoverFlowCarouselView(mRootView);
        mEmptyView= createEmptyView(mRootView);

        mAdapter = createListViewAdapter();
        mAdapter.supportLoader=false;

        mCoverFlow.setOnItemSelectedListener(new CoverFlowCarousel.OnItemSelectedListener() {
            public void onItemSelected(View child, int position) {
                Object iBaseObject = getDataManager().get(position);
                onPageChange(iBaseObject, position);

                if (getDataManager().canLoadNext() && position == getDataManager().size() - 1)
                    loadNext();

                if (getDataManager().canLoadRefresh() && position == 0) {
                    if (getSwipeRefreshLayout() != null)
                        getSwipeRefreshLayout().setEnabled(true);
                    else
                        loadRefreshData();
                } else if (getSwipeRefreshLayout() != null)
                    getSwipeRefreshLayout().setEnabled(false);
            }
        });

        mCoverFlow.setSpacing(0.5f);
        if(mRootView.findViewById(R.id.progressBar)!=null)
            mProgressBar= (ProgressView)mRootView.findViewById(R.id.progressBar);

        configListView(mCoverFlow, mAdapter);

        loadNext();
        onViewCreated(mRootView);
        return mRootView;
    }
    protected void onPageChange(Object itemPosition,int position)
    {

    }
    protected void configListView(CoverFlowCarousel mList,BaseListViewAdapter mAdapter)
    {

    }

    protected CoverFlowCarousel createCoverFlowCarouselView(View rootView)
    {
        return (CoverFlowCarousel) rootView.findViewById(R.id.listView);
    }

    public void onCallStart()
    {
        super.onCallStart();
        if(mProgressBar!=null && firstItemIn==false)
            mProgressBar.setVisibility(View.VISIBLE);
    }
    public void onCallEnd(List<?> dataList,boolean isPreviousPage)
    {
        super.onCallEnd(dataList, isPreviousPage);
        final Handler handler = new Handler();
        final Runnable runnablelocal = new Runnable() {
            @Override
            public void run() {
                if(firstDataIn) {
                    mCoverFlow.setAdapter(mAdapter);
                    mCoverFlow.invalidate();
                    firstDataIn=false;
                }
            }
        };
        handler.postDelayed(runnablelocal, 1000);

        if(mProgressBar!=null)
            mProgressBar.setVisibility(View.GONE);
        firstItemIn=true;
    }

    public void onDataReceived(String url,String value,Object status)
    {
        super.onDataReceived(url,value,status);

    }
}
