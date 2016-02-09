package com.kimeeo.library.listDataView.viewPager;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.BaseListDataView;
import com.kimeeo.library.listDataView.viewPager.directionalviewpager.DirectionalViewPager;
import com.kimeeo.library.listDataView.viewPager.jazzyViewPager.JazzyViewPager;
import com.kimeeo.library.listDataView.viewPager.viewPager.BaseViewPagerAdapter;
import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.viewpagerindicator.PageIndicator;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
abstract public class BaseViewPager extends BaseListDataView implements ViewPager.OnPageChangeListener {
    protected void garbageCollectorCall() {
        super.garbageCollectorCall();
        mRootView=null;
        mEmptyView=null;
        if(mAdapter!=null)
            mAdapter.garbageCollectorCall();
        mAdapter=null;
        mViewPager=null;
        mSwipeRefreshLayout=null;
        transition = null;
        mIndicator= null;
        mEmptyViewImage= null;
        mEmptyViewMessage= null;
    }

    public View getRootView() {
        return mRootView;
    }

    protected View mRootView;
    protected View mEmptyView;

    public BaseViewPagerAdapter getAdapter() {
        return mAdapter;
    }

    protected BaseViewPagerAdapter mAdapter;
    protected ViewPager mViewPager;

    public ViewPager getViewPager() {
        return mViewPager;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    public SwipeRefreshLayout getSwipeRefreshLayout()
    {
        return mSwipeRefreshLayout;
    }
    protected JazzyViewPager.TransitionEffect transition = null;
    protected View mIndicator;
    protected ImageView mEmptyViewImage;
    protected TextView mEmptyViewMessage;

    public String getItemTitle(int position,Object navigationObject)
    {
        return "";
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        configViewParam();
        transition = createTransitionEffect();

        mRootView = createRootView(inflater, container, savedInstanceState);
        if(getDataManager().getRefreshEnabled())
            configSwipeRefreshLayout(createSwipeRefreshLayout(mRootView));

        mViewPager = createViewPager(mRootView);
        mEmptyView = createEmptyView(mRootView);
        createAdapter(mViewPager);

        mIndicator = createIndicator(mRootView);
        setUpIndicator(mIndicator, mViewPager);

        mViewPager.addOnPageChangeListener(this);

        loadNext();
        configViewPager(mViewPager, mAdapter, mIndicator);

        if(mViewPager instanceof JazzyViewPager && getTransitionEffect()!=null)
            configJazzyViewPager(((JazzyViewPager)mViewPager));

        onViewCreated(mRootView);
        return mRootView;
    }

    protected JazzyViewPager.TransitionEffect createTransitionEffect() {
        return null;
    }

    protected void createAdapter(ViewPager mViewPager) {
        mAdapter = createViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
    }

    //Confgi Your your viewpager here
    protected void configViewPager(ViewPager mList, BaseViewPagerAdapter mAdapter, View indicator) {

    }

    public void onViewCreated(View view) {

    }

    protected Drawable getEmptyViewDrawable() {
        Drawable drawable = getResources().getDrawable(R.drawable._empty_box);
        drawable.setColorFilter(getResources().getColor(R.color._emptyViewMessageColor), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    protected String getEmptyViewMessage() {
        return getResources().getString(R.string._emptyViewMessage);
    }

    public ImageView getEmptyImageView(View rootView) {
        return mEmptyViewImage;
    }

    public TextView getEmptyMessageView(View rootView) {
        return mEmptyViewMessage;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    protected View createEmptyView(View rootView) {
        View emptyView = rootView.findViewById(R.id.emptyView);
        if (rootView.findViewById(R.id.emptyViewImage) != null && rootView.findViewById(R.id.emptyViewImage) instanceof ImageView) {
            mEmptyViewImage = (ImageView) rootView.findViewById(R.id.emptyViewImage);
            mEmptyViewImage.setImageDrawable(getEmptyViewDrawable());
        }

        if (rootView.findViewById(R.id.emptyViewMessage) != null && rootView.findViewById(R.id.emptyViewMessage) instanceof TextView) {
            mEmptyViewMessage = (TextView) rootView.findViewById(R.id.emptyViewMessage);
            mEmptyViewMessage.setText(getEmptyViewMessage());
        }

        if (emptyView != null)
            emptyView.setVisibility(View.GONE);
        return emptyView;
    }

    protected void configSwipeRefreshLayout(SwipeRefreshLayout view) {
        mSwipeRefreshLayout = view;
        if (mSwipeRefreshLayout != null) {
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

    protected SwipeRefreshLayout createSwipeRefreshLayout(View rootView) {
        if (rootView.findViewById(R.id.swipeRefreshLayout) != null) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            return swipeRefreshLayout;
        }
        return null;
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        if (getTransitionEffect() != null) {
            if(getDataManager().getRefreshEnabled())
                rootView = inflater.inflate(R.layout._fragment_page_view_effects_with_swipe_refresh_layout, container, false);
            else
                rootView = inflater.inflate(R.layout._fragment_page_view_effects, container, false);
        }
        else {
            if(getDataManager().getRefreshEnabled())
                rootView = inflater.inflate(R.layout._fragment_page_view_with_swipe_refresh_layout, container, false);
            else
                rootView = inflater.inflate(R.layout._fragment_page_view, container, false);
        }
        return rootView;
    }


    abstract protected BaseViewPagerAdapter createViewPagerAdapter();
    public String getPageTitle(int position, Object o)
    {
        return "";
    }

    protected void configJazzyViewPager(JazzyViewPager mJazzy) {
        JazzyViewPager.TransitionEffect transitionEffect = getTransitionEffect();
        mJazzy.setTransitionEffect(transitionEffect);
    }

    public JazzyViewPager.TransitionEffect getTransitionEffect() {
        return transition;
    }

    public void setTransitionEffect(JazzyViewPager.TransitionEffect transition) {

        this.transition = transition;
        if (mViewPager instanceof JazzyViewPager)
            configJazzyViewPager((JazzyViewPager) mViewPager);
    }

    protected ViewPager createViewPager(View rootView) {
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        return viewPager;
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
                    SmartTabLayout.TabProvider tabProvider = getTabProvider();
                    if (tabProvider != null)
                        pageIndicator.setCustomTabView(tabProvider);
                } else if (indicator instanceof RecyclerTabLayout) {
                    RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) indicator;

                    RecyclerTabLayout.Adapter<?> tabProvider = getRecyclerViewTabProvider(viewPager);
                    if (tabProvider != null)
                        recyclerTabLayout.setUpWithAdapter(tabProvider);
                    else
                        recyclerTabLayout.setUpWithViewPager(viewPager);

                }
                else if (indicator instanceof TabLayout && getDataManager().size()!=0) {
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
            else if (indicator instanceof TabLayout  && getDataManager().size()!=0) {
                TabLayout tabLayout = (TabLayout) indicator;
                tabLayout.setupWithViewPager(viewPager);
                configTabLayout(tabLayout, viewPager);
            }
        }

    }
    protected void configTabLayout(TabLayout tabLayout,ViewPager viewPager)
    {

    }

    protected RecyclerTabLayout.Adapter<?> getRecyclerViewTabProvider(ViewPager viewPager) {
        return null;
    }
    //SegmentTabLayout segmentTabLayout;






    protected SmartTabLayout.TabProvider getTabProvider() {
        /*
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        final Resources res = getActivity().getResources();

        SmartTabLayout.TabProvider tabProvider = new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView icon = (ImageView) inflater.inflate(R.layout.custom_tab_icon2, container, false);
                switch (position) {
                    case 0:
                        icon.setImageDrawable(res.getDrawable(R.drawable.ic_home_white_24dp));
                        break;
                    case 1:
                        icon.setImageDrawable(res.getDrawable(R.drawable.ic_search_white_24dp));
                        break;
                    case 2:
                        icon.setImageDrawable(res.getDrawable(R.drawable.ic_person_white_24dp));
                        break;
                    case 3:
                        icon.setImageDrawable(res.getDrawable(R.drawable.ic_flash_on_white_24dp));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return icon;
            }
        };
        return  tabProvider;
        */
        return null;
    }

    protected View createIndicator(View rootView) {
        return rootView.findViewById(R.id.indicator);
    }


    protected void onPageChange(Object itemPosition, int position) {

    }

    public void onPageSelected(int position) {
        if (mViewPager != null) {
            Object iBaseObject = getDataManager().get(position);
            onPageChange(iBaseObject, position);
            setCurrentItem(position);

            if (getDataManager().canLoadNext() && position == getDataManager().size() - 1)
                loadNext();


            updateRefreshEnabled(position);

        }
    }

    private void updateRefreshEnabled(int position) {
        if (getDataManager().canLoadRefresh() && getDataManager().canLoadRefresh() && position == 0) {
            if (mSwipeRefreshLayout != null)
                mSwipeRefreshLayout.setEnabled(true);
            else
                loadRefreshData();
        }
        else if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setEnabled(false);
    }


    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public void onPageScrollStateChanged(int arg0) {
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

    private int currentItem;

    protected void setCurrentItem(int value) {
        currentItem = value;
    }

    protected int getCurrentItem() {
        return currentItem;
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
                mSwipeRefreshLayout.setEnabled(getDataManager().hasScopeOfRefresh());
        }
    }

    public void onDataLoadError(String url, Object status) {
        if (mEmptyView != null) {
            if (getDataManager().size() == 0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }
        updateSwipeRefreshLayout(false);
    }

    public void onCallEnd(List<?> dataList, final boolean isRefreshData) {

        if(dataList!=null && dataList.size()!=0 && getViewPager()!=null)
        {
            final Handler handler = new Handler();
            final Runnable runnablelocal = new Runnable() {
                @Override
                public void run() {


                    setUpIndicator(mIndicator, mViewPager);

                    if (isRefreshData) {
                        if(getViewPager() instanceof DirectionalViewPager==false)
                        {
                            //getViewPager().setAdapter(null);
                            getViewPager().setAdapter(getAdapter());
                            gotoItem(0, true);
                        }
                    }
                }
            };
            handler.postDelayed(runnablelocal, 1000);
        }



        if (mEmptyView != null) {
            if (getDataManager().size() == 0)
                mEmptyView.setVisibility(View.VISIBLE);
            else
                mEmptyView.setVisibility(View.GONE);
        }

        updateSwipeRefreshLayout(isRefreshData);
    }


    public void onItemClick(View view, int position) {
        Object baseObject = getDataManager().get(position);
        onItemClick(baseObject);
    }

    public void onItemClick(Object baseObject) {

    }
}
