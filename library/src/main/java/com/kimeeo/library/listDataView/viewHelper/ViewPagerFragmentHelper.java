package com.kimeeo.library.listDataView.viewHelper;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.viewPager.directionalviewpager.DirectionalViewPager;
import com.kimeeo.library.listDataView.viewPager.fragmentPager.BaseFragmentViewPagerAdapter;
import com.kimeeo.library.listDataView.viewPager.jazzyViewPager.JazzyViewPager;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class ViewPagerFragmentHelper extends ViewPagerHelper {

    protected BaseFragmentViewPagerAdapter mFragmentAdapter;
    public ViewPagerFragmentHelper fragmentAdapter(BaseFragmentViewPagerAdapter mFragmentAdapter) {
        this.mFragmentAdapter=mFragmentAdapter;
        this.mFragmentAdapter.setOnCallService(this);
        return this;
    }

    public void create() throws Exception{
        if(mFragmentAdapter==null)
            throw new Exception("Must have mFragmentAdapter");
        else if(mViewPager==null)
            throw new Exception("Must have BaseViewPager");
        else if(dataManager==null)
            throw new Exception("Must have dataManager");

        if(dataManager.getRefreshEnabled())
            configSwipeRefreshLayout(mSwipeRefreshLayout);


        mViewPager.setAdapter(mFragmentAdapter);

        if(mIndicator!=null)
            setUpIndicator(mIndicator, mViewPager);

        mViewPager.addOnPageChangeListener(this);

        loadNext();
        configViewPager(mViewPager, mFragmentAdapter, mIndicator,dataManager);

        if(mViewPager instanceof JazzyViewPager && transition!=null)
            configJazzyViewPager(((JazzyViewPager) mViewPager));
    }

    protected void configViewPager(ViewPager mList, BaseFragmentViewPagerAdapter mAdapter, View indicator,DataManager dataManager) {

    }
    protected SegmentTabLayout segmentTabLayout;
    protected void setUpIndicator(View indicator, ViewPager viewPager) {
        if (indicator != null && indicator instanceof SegmentTabLayout) {
            segmentTabLayout= (SegmentTabLayout) indicator;
            segmentTabLayout.setTabData(getTabData());
            segmentTabLayout.setOnTabSelectListener(onTabSelectListener);
            viewPager.setOnPageChangeListener(this);
        }
        else
            super.setUpIndicator(indicator,viewPager);
    }
    protected void setCurrentItem(int value) {
        super.setCurrentItem(value);
        if(segmentTabLayout!=null)
            segmentTabLayout.setCurrentTab(value);
    }

    protected String[] getTabData() {
        if(dataManager.size()==0) {
            String[] list = new String[1];
            list[0]="";
            return list;
        }
        else {
            String[] list = new String[dataManager.size()];
            for (int i = 0; i < dataManager.size(); i++) {
                list[i] = mFragmentAdapter.getPageTitle(i) + "";
            }
            return list;
        }
    }
    public void onCallEnd(List<?> dataList, final boolean isRefreshData) {

        if(dataList!=null && dataList.size()!=0 && mViewPager!=null)
        {
            final Handler handler = new Handler();
            final Runnable runnablelocal = new Runnable() {
                @Override
                public void run() {


                    setUpIndicator(mIndicator, mViewPager);

                    if (isRefreshData) {
                        if(mViewPager instanceof DirectionalViewPager ==false)
                        {
                            mViewPager.setAdapter(mFragmentAdapter);
                            gotoItem(0, true);
                        }
                    }
                }
            };
            handler.postDelayed(runnablelocal, 1000);
        }



        if (mEmptyView != null) {
            if (dataManager.size() == 0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }

        updateSwipeRefreshLayout(isRefreshData);
    }


    OnTabSelectListener onTabSelectListener = new OnTabSelectListener(){
        public void onTabSelect(int position)
        {
            gotoItem(position,true);
            setCurrentItem(position);
        }
        public void onTabReselect(int position)
        {
            gotoItem(position,true);
            setCurrentItem(position);
        }
    };
}
