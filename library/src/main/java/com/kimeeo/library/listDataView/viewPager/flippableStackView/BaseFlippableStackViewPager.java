package com.kimeeo.library.listDataView.viewPager.flippableStackView;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.flippablestackview.FlippableStackView;
import com.bartoszlipinski.flippablestackview.StackPageTransformer;
import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.viewPager.BaseViewPager;
import com.kimeeo.library.listDataView.viewPager.viewPager.BaseViewPagerAdapter;
import com.rey.material.widget.ProgressView;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/22/16.
 */
abstract public class BaseFlippableStackViewPager extends BaseViewPager
{
    private FlippableStackView mViewPager;
    private ProgressView mProgressBar;
    private boolean firstItemIn = false;
    private boolean firstDataIn = true;

    protected void garbageCollectorCall() {
        super.garbageCollectorCall();
        mViewPager=null;
        mProgressBar=null;

    }
    protected View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getDataManager().getRefreshEnabled())
            return inflater.inflate(R.layout._fragment_fliper_page_view_with_swipe_refresh_layout, container, false);
        else
            return inflater.inflate(R.layout._fragment_fliper_page_view, container, false);
    }
    protected FlippableStackView createFlippableStackView(View rootView)
    {
        FlippableStackView viewPager = (FlippableStackView)rootView.findViewById(R.id.viewPager);
        return viewPager;
    }
    abstract public StackPageTransformer.Orientation getOrientation();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        configViewParam();

        mRootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        mViewPager = createFlippableStackView(mRootView);

        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.initStack(4, getOrientation());

        mEmptyView = createEmptyView(mRootView);
        createAdapter(mViewPager);



        loadNext();
        configViewPager(mViewPager, mAdapter, mIndicator);

        if(mRootView.findViewById(R.id.progressBar)!=null)
            mProgressBar= (ProgressView)mRootView.findViewById(R.id.progressBar);


        onViewCreated(mRootView);
        return mRootView;
    }
    protected void configSwipeRefreshLayout(SwipeRefreshLayout view) {
        super.configSwipeRefreshLayout(view);
        /*
        if(view!=null)
            view.setEnabled(false);
        */
    }
    protected void configViewPager(FlippableStackView mList, BaseViewPagerAdapter mAdapter, View indicator) {

    }

    protected void createAdapter(FlippableStackView mViewPager) {
        mAdapter = createViewPagerAdapter();
        //mAdapter.supportLoader=false;

    }

    public void onPageSelected(int position)
    {
        if(mViewPager!=null)
        {
            Object iBaseObject = getDataManager().get(position);
            onPageChange(iBaseObject, position);

            if (getDataManager().canLoadNext() && position == getDataManager().size() - 1)
                loadNext();


            if (getDataManager().canLoadRefresh() && position == 0) {
                if (getSwipeRefreshLayout() != null) {
                    loadRefreshData();

                    getSwipeRefreshLayout().setEnabled(true);
                }
                else
                    loadRefreshData();
            } else if (getSwipeRefreshLayout() != null) {
                getSwipeRefreshLayout().setEnabled(false);
            }
        }
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
                    mViewPager.setAdapter(mAdapter);
                    mViewPager.invalidate();
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
    }
}
