package com.kimeeo.library.listDataView.listViews.mosaicList;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adhamenaya.listeners.OnItemClickListener;
import com.adhamenaya.views.MosaicLayout;
import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.listViews.BaseListView;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.rey.material.widget.ProgressView;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/28/16.
 */
abstract public class BaseMosaicList extends BaseListView implements OnItemClickListener {

    private boolean firstItemIn = false;
    private boolean firstDataIn = true;
    private ProgressView mProgressBar;
    private MosaicLayout mMosaicLayout;

    public MosaicLayout getMosaicLayout() {
        return mMosaicLayout;
    }

    protected void garbageCollectorCall() {
        super.garbageCollectorCall();
        mProgressBar = null;
        mMosaicLayout = null;
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        if(getDataManager().getRefreshEnabled())
            rootView = inflater.inflate(R.layout._fragment_mocaic_page_view_with_swipe_refresh_layout, container, false);
        else
            rootView = inflater.inflate(R.layout._fragment_mocaic_page_view, container, false);
        return rootView;
    }

    protected MosaicLayout createMosaicLayout(View rootView) {
        return (MosaicLayout) rootView.findViewById(R.id.listView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        configViewParam();
        mRootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        mMosaicLayout = createMosaicLayout(mRootView);
        mEmptyViewHelper = createEmptyViewHelper();
        mAdapter = createListViewAdapter();
        mAdapter.supportLoader = false;

        mMosaicLayout.setOnItemClickListener(this);
        setOnScrollListener(mMosaicLayout);
        mMosaicLayout.chooseRandomPattern(true);
        configMosaicLayout(mMosaicLayout, mAdapter);


        if (mRootView.findViewById(R.id.progressBar) != null)
            mProgressBar = (ProgressView) mRootView.findViewById(R.id.progressBar);


        loadNext();
        onViewCreated(mRootView);
        return mRootView;
    }
    public void onClick(int position)
    {
        onItemClick(getDataManager().get(position));
    }


    protected void setOnScrollListener(MosaicLayout mList) {
        mList.setListener(new MosaicLayout.OnBottomReach() {
            public void onBottomReached(int l, int t, int oldl, int oldt) {
                if (getDataManager().canLoadNext())
                    loadNext();
                onDataScroll(getMosaicLayout(), l, t);
            }
        });
    }
    protected void onDataScroll(MosaicLayout listView, int dx, int dy)
    {

    }

    protected void configMosaicLayout(MosaicLayout mList,BaseListViewAdapter mAdapter)
    {

    }
    public void onCallEnd(List<?> dataList,boolean isPreviousPage)
    {
        super.onCallEnd(dataList, isPreviousPage);
        final Handler handler = new Handler();
        final Runnable runnablelocal = new Runnable() {
            @Override
            public void run() {

                mMosaicLayout.setAdapter(mAdapter);
                mMosaicLayout.invalidate();
                firstDataIn=false;
            }
        };
        handler.postDelayed(runnablelocal, 1000);

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
