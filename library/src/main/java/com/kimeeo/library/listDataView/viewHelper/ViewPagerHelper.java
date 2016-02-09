package com.kimeeo.library.listDataView.viewHelper;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.library.R;
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
public class ViewPagerHelper implements OnCallService, ViewPager.OnPageChangeListener{


    protected SwipeRefreshLayout mSwipeRefreshLayout;
    public ViewPagerHelper swipeRefreshLayout(SwipeRefreshLayout view)
    {
        mSwipeRefreshLayout =view;
        return this;
    }
    protected DataManager dataManager;
    public ViewPagerHelper dataManager(DataManager dataManager)
    {
        this.dataManager =dataManager;
        return this;
    }

    protected JazzyViewPager.TransitionEffect transition;
    public ViewPagerHelper transitionEffect(JazzyViewPager.TransitionEffect transition) {
        this.transition =transition;
        return this;
    }


    protected View mEmptyView;
    public ViewPagerHelper emptyView(View view)
    {
        mEmptyView=view;
        if(mEmptyView!=null)
            mEmptyView.setVisibility(View.GONE);

        return this;
    }





    protected ImageView mEmptyViewImage;

    public ViewPagerHelper emptyImageView(ImageView view)
    {
        mEmptyViewImage=view;

        if(mEmptyViewImage!=null && emptyViewDrawable!=null)
            mEmptyViewImage.setImageDrawable(emptyViewDrawable);


        return this;
    }

    protected TextView mEmptyViewMessage;
    public ViewPagerHelper emptyMessageView(TextView view)
    {
        mEmptyViewMessage=view;
        if(mEmptyViewMessage!=null && emptyViewMessage!=null)
            mEmptyViewMessage.setText(emptyViewMessage);
        return this;
    }
    Drawable emptyViewDrawable;
    protected ViewPagerHelper emptyViewDrawable(Drawable drawable)
    {
        emptyViewDrawable=drawable;
        if(mEmptyViewImage!=null && emptyViewDrawable!=null)
            mEmptyViewImage.setImageDrawable(emptyViewDrawable);
        return this;
    }
    String emptyViewMessage;
    protected ViewPagerHelper emptyViewMessage(String emptyViewMessage)
    {
        this.emptyViewMessage = emptyViewMessage;
        if(mEmptyViewMessage!=null && emptyViewMessage!=null)
            mEmptyViewMessage.setText(emptyViewMessage);
        return this;
    }

    protected BaseViewPagerAdapter mAdapter;
    public ViewPagerHelper adapter(BaseViewPagerAdapter adapter)
    {
        this.mAdapter = adapter;
        this.mAdapter.setOnCallService(this);
        return this;
    }


    protected ViewPager mViewPager;
    public ViewPagerHelper with(ViewPager view)
    {
        this.mViewPager =view;
        return this;
    }
    protected View mIndicator;
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
    private SmartTabLayout.TabProvider tabProvider;
    public ViewPagerHelper tabProvider(SmartTabLayout.TabProvider tabProvider)
    {
        this.tabProvider = tabProvider;
        return this;
    }
    protected void configViewPager(ViewPager mList, BaseViewPagerAdapter mAdapter, View indicator,DataManager dataManager) {

    }



    private RecyclerTabLayout.Adapter<?> recyclerViewTabProvider;
    public ViewPagerHelper tabProvider(RecyclerTabLayout.Adapter<?> tabProvider)
    {
        this.recyclerViewTabProvider = tabProvider;
        return this;
    }

    private boolean isIndicatorSet=false;
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
    private int currentItem;

    protected void setCurrentItem(int value) {
        currentItem = value;
    }

    protected int getCurrentItem() {
        return currentItem;
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
        if (mEmptyView != null)
            mEmptyView.setVisibility(View.GONE);
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
        if (mEmptyView != null) {
            if (dataManager.size() == 0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }
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



        if (mEmptyView != null) {
            if (dataManager.size() == 0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }

        updateSwipeRefreshLayout(isRefreshData);
    }
}
