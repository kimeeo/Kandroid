package com.kimeeo.library.listDataView.listViews.stackView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.StackView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.listViews.BaseListView;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.rey.material.widget.ProgressView;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
abstract public class BaseStackView extends BaseListView {

    private boolean firstItemIn = false;
    private boolean firstDataIn = true;


    private ProgressView mProgressBar;
    private StackView mStackView;
    protected void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        mProgressBar=null;
        mStackView=null;
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        if(getDataManager().getRefreshEnabled())
            return inflater.inflate(R.layout._fragment_stack_view_with_swipe_refresh_layout, container, false);
        else
            return inflater.inflate(R.layout._fragment_stack_view, container, false);
    }
    protected StackView createStackView(View rootView)
    {
        return (StackView) rootView.findViewById(R.id.listView);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        configViewParam();
        mRootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        mStackView = createStackView(mRootView);
        mEmptyView= createEmptyView(mRootView);
        mAdapter = createListViewAdapter();
        mAdapter.supportLoader=false;
        getDataManager().setRefreshEnabled(false);
        mStackView.setAdapter(mAdapter);

        mStackView.setOnItemSelectedListener(new StackView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        configmStackView(mStackView, mAdapter);


        if(mRootView.findViewById(R.id.progressBar)!=null)
            mProgressBar= (ProgressView)mRootView.findViewById(R.id.progressBar);




        loadNext();
        onViewCreated(mRootView);
        return mRootView;
    }
    protected void onPageChange(Object itemPosition,int position)
    {

    }

    protected void configmStackView(StackView mList,BaseListViewAdapter mAdapter)
    {

    }

    public void onCallEnd(List<?> dataList,final boolean isRefreshData)
    {
        super.onCallEnd(dataList, isRefreshData);
        final Handler handler = new Handler();
        final Runnable runnablelocal = new Runnable() {
            @Override
            public void run() {
                if(firstDataIn) {

                    mStackView.invalidate();
                    firstDataIn=false;
                }
            }
        };
        handler.postDelayed(runnablelocal, 1000);

        if(mProgressBar!=null)
            mProgressBar.setVisibility(View.GONE);
        firstItemIn = true;


    }

    public void onDataReceived(String url,String value,Object status)
    {
        super.onDataReceived(url, value, status);
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

