package com.kimeeo.library.listDataView.viewHelper;

import android.content.res.Resources;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.EmptyViewHelper;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.viewPager.directionalviewpager.DirectionalViewPager;
import com.kimeeo.library.listDataView.viewPager.jazzyViewPager.JazzyViewPager;
import com.kimeeo.library.listDataView.viewPager.viewPager.BaseViewPagerAdapter;
import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.viewpagerindicator.PageIndicator;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class ViewPagerHelper extends BaseHelper implements OnCallService, ViewPager.OnPageChangeListener {
    protected EmptyViewHelper mEmptyViewHelper;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected DataManager dataManager;
    protected JazzyViewPager.TransitionEffect transition;
    protected BaseViewPagerAdapter mAdapter;
    protected ViewPager mViewPager;
    protected View mIndicator;
    private SmartTabLayout.TabProvider tabProvider;
    private RecyclerTabLayout.Adapter<?> recyclerViewTabProvider;
    private boolean isIndicatorSet = false;
    private int currentItem;

    public Resources getResources()
    {
        return mViewPager.getResources();
    }

    public void retry() {
        loadNext();
    }

    public ViewPagerHelper emptyView(View view)
    {
        mEmptyViewHelper = new EmptyViewHelper(view.getContext(), view, this, true, true);
        return this;
    }

    public ViewPagerHelper emptyView(EmptyViewHelper emptyViewHelper) {
        mEmptyViewHelper = emptyViewHelper;
        return this;
    }

    protected void clear()
    {
        if (dataManager != null) {
            dataManager.garbageCollectorCall();
            dataManager = null;
        }
        if (mAdapter != null)
            mAdapter.garbageCollectorCall();

        mAdapter = null;
        mViewPager = null;
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.clean();
        mEmptyViewHelper = null;
        mSwipeRefreshLayout = null;
    }

    public ViewPagerHelper swipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout = view;
        return this;
    }

    public ViewPagerHelper dataManager(DataManager dataManager)
    {
        this.dataManager = dataManager;
        return this;
    }

    public ViewPagerHelper transitionEffect(JazzyViewPager.TransitionEffect transition) {
        this.transition = transition;
        return this;
    }

    public ViewPagerHelper adapter(BaseViewPagerAdapter adapter)
    {
        this.mAdapter = adapter;
        this.mAdapter.setOnCallService(this);
        return this;
    }

    public ViewPagerHelper with(ViewPager view)
    {
        this.mViewPager =view;
        return this;
    }

    public ViewPagerHelper indicator(View view)
    {
        mIndicator = view;
        return this;
    }

    public void create() throws Exception{

        if(mAdapter==null)
            throw new Exception("Must have Adapter");
        else if(mViewPager==null)
            throw new Exception("Must have BaseViewPager");
        else if(dataManager==null)
            throw new Exception("Must have dataManager");

        if(dataManager.getRefreshEnabled())
            configSwipeRefreshLayout(mSwipeRefreshLayout);


        mViewPager.setAdapter(mAdapter);

        if(mIndicator!=null)
            setUpIndicator(mIndicator, mViewPager);

        mViewPager.addOnPageChangeListener(this);

        loadNext();
        configViewPager(mViewPager, mAdapter, mIndicator,dataManager);

        if(mViewPager instanceof JazzyViewPager && transition!=null)
            configJazzyViewPager(((JazzyViewPager) mViewPager));
    }

    protected void configJazzyViewPager(JazzyViewPager mJazzy) {
        mJazzy.setTransitionEffect(transition);
    }

    protected void configSwipeRefreshLayout(SwipeRefreshLayout view) {
        mSwipeRefreshLayout = view;
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (dataManager.canLoadRefresh())
                        loadRefreshData();
                    else
                        mSwipeRefreshLayout.setRefreshing(false);

                    mSwipeRefreshLayout.setEnabled(dataManager.hasScopeOfRefresh());
                }
            });
            boolean refreshEnabled = dataManager.getRefreshEnabled();
            mSwipeRefreshLayout.setEnabled(refreshEnabled);
            mSwipeRefreshLayout.setColorSchemeColors(R.array.progressColors);
        }
    }

    public ViewPagerHelper loadNext(){
        dataManager.loadNext();
        return this;
    }

    public ViewPagerHelper loadRefreshData()
    {
        dataManager.loadRefreshData();
        return this;
    }

    public ViewPagerHelper tabProvider(SmartTabLayout.TabProvider tabProvider)
    {
        this.tabProvider = tabProvider;
        return this;
    }

    protected void configViewPager(ViewPager mList, BaseViewPagerAdapter mAdapter, View indicator,DataManager dataManager) {

    }

    public ViewPagerHelper tabProvider(RecyclerTabLayout.Adapter<?> tabProvider)
    {
        this.recyclerViewTabProvider = tabProvider;
        return this;
    }

    protected void setUpIndicator(View indicator, ViewPager viewPager) {
        if(indicator!=null) {
            if(isIndicatorSet==false) {
                if (indicator instanceof PageIndicator) {
                    PageIndicator pageIndicator = (PageIndicator) indicator;
                    pageIndicator.setViewPager(viewPager);

                } else if (indicator instanceof SmartTabLayout) {
                    SmartTabLayout pageIndicator = (SmartTabLayout) indicator;
                    pageIndicator.setViewPager(viewPager);
                    if (tabProvider != null)
                        pageIndicator.setCustomTabView(tabProvider);
                } else if (indicator instanceof RecyclerTabLayout) {
                    RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) indicator;


                    if (recyclerViewTabProvider != null)
                        recyclerTabLayout.setUpWithAdapter(recyclerViewTabProvider);
                    else
                        recyclerTabLayout.setUpWithViewPager(viewPager);
                }
                else if (indicator instanceof TabLayout && dataManager.size()!=0) {
                    TabLayout tabLayout = (TabLayout) indicator;
                    tabLayout.setupWithViewPager(viewPager);
                    configTabLayout(tabLayout,viewPager);
                }
                isIndicatorSet = true;
            }else if (indicator instanceof SmartTabLayout) {
                SmartTabLayout pageIndicator = (SmartTabLayout) indicator;
                pageIndicator.invalidate();
            }else if (indicator instanceof RecyclerTabLayout) {
                RecyclerTabLayout pageIndicator = (RecyclerTabLayout) indicator;
                pageIndicator.getAdapter().notifyDataSetChanged();
                pageIndicator.invalidate();
            }else if (indicator instanceof PageIndicator) {
                PageIndicator pageIndicator = (PageIndicator) indicator;
                pageIndicator.notifyDataSetChanged();
            }
            else if (indicator instanceof TabLayout && dataManager.size()!=0) {
                TabLayout tabLayout = (TabLayout) indicator;
                tabLayout.setupWithViewPager(viewPager);
                configTabLayout(tabLayout, viewPager);
            }

        }
    }

    protected void configTabLayout(TabLayout tabLayout,ViewPager viewPager)
    {

    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public void onPageScrollStateChanged(int arg0) {
    }

    protected void onPageChange(Object itemPosition, int position) {

    }

    protected int getCurrentItem() {
        return currentItem;
    }

    protected void setCurrentItem(int value) {
        currentItem = value;
    }

    public void onPageSelected(int position) {
        if (mViewPager != null) {
            Object iBaseObject = dataManager.get(position);
            onPageChange(iBaseObject, position);
            setCurrentItem(position);

            if (dataManager.canLoadNext() && position == dataManager.size() - 1)
                loadNext();


            updateRefreshEnabled(position);

        }
    }

    private void updateRefreshEnabled(int position) {
        if (dataManager.canLoadRefresh() && dataManager.canLoadRefresh() && position == 0) {
            if (mSwipeRefreshLayout != null)
                mSwipeRefreshLayout.setEnabled(true);
            else
                loadRefreshData();
        }
        else if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setEnabled(false);
    }

    public void gotoItem(int value, Boolean scroll) {
        if (mViewPager != null)
            mViewPager.setCurrentItem(value, scroll);
        setCurrentItem(value);
    }

    public void goBack() {
        int index = -1;
        int currentIndex = getCurrentItem();
        if (currentIndex != 0)
            index = currentIndex - 1;
        else {
            if (mViewPager != null)
                index = mViewPager.getAdapter().getCount() - 1;

        }
        gotoItem(index, true);
    }

    public void goNext() {
        int index = -1;
        int currentIndex = getCurrentItem();
        int total = 0;
        if (mViewPager != null)
            total = mViewPager.getAdapter().getCount() - 1;
        if (currentIndex != total)
            index = currentIndex + 1;
        else
            index = 0;
        gotoItem(index, true);
    }

    public void onDataReceived(String url, Object value, Object status) {

    }

    public void onCallStart() {
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updatesStart();
    }

    public void onFirstCallEnd() {

    }

    public void onLastCallEnd() {

    }


    public void updateSwipeRefreshLayout(boolean isRefreshData) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);

            if (isRefreshData)
                mSwipeRefreshLayout.setEnabled(dataManager.hasScopeOfRefresh());
        }
    }

    public void onDataLoadError(String url, Object status) {
        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updateView(dataManager);

        updateSwipeRefreshLayout(false);
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
                            mViewPager.setAdapter(mAdapter);
                            gotoItem(0, true);
                        }
                    }
                }
            };
            handler.postDelayed(runnablelocal, 1000);
        }


        if (mEmptyViewHelper != null)
            mEmptyViewHelper.updateView(dataManager);

        updateSwipeRefreshLayout(isRefreshData);
    }
}
