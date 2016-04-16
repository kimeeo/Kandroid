package com.kimeeo.library.listDataView.recyclerView.viewPager;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.DefaultRecyclerView;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lsjwzh.widget.recyclerviewpager.TabLayoutSupport;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 2/3/16.
 */
abstract public class BaseViewPager extends DefaultRecyclerView {

    protected void configRecyclerView(RecyclerView mList,BaseRecyclerViewAdapter mAdapter)
    {
        mAdapter.supportLoader=false;
    }
    protected void configDataManager(DataManager dataManager) {
        dataManager.setPagingSupport(false);
    }
    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        final TabLayout tabLayout = createTabLayout(getRootView());
        if(tabLayout!=null) {
            tabLayout.setVisibility(View.GONE);
        }
    }

    protected void updateIndicator(final RecyclerView mList,final BaseRecyclerViewAdapter mAdapter)
    {
        if(mList instanceof RecyclerViewPager && getDataManager().getPagingSupport()==false)
        {
            final TabLayout tabLayout = createTabLayout(getRootView());
            if(tabLayout!=null) {
                tabLayout.setVisibility(View.VISIBLE);

                final Handler handler = new Handler();
                final Runnable runnablelocal = new Runnable() {
                    @Override
                    public void run() {
                        final int selected = tabLayout.getSelectedTabPosition();
                        TabLayoutSupport.setupWithViewPager(tabLayout, (RecyclerViewPager) mList, mAdapter);
                        if (selected > 0) {
                            tabLayout.getTabAt(selected).select();

                            final Handler handler = new Handler();
                            final Runnable runnablelocal = new Runnable() {
                                @Override
                                public void run() {
                                    tabLayout.setScrollPosition(selected, Float.parseFloat("0.3"), true);
                                }};
                            handler.postDelayed(runnablelocal, 300);

                        }
                        configTabLayout(tabLayout, (RecyclerViewPager) mList);
                    }
                };
                handler.postDelayed(runnablelocal, 700);
            }
        }
    }

    protected void configTabLayout(TabLayout tabLayout, RecyclerViewPager mList) {

    }

    public void onCallEnd(List<?> dataList,final boolean isRefreshData) {
        super.onCallEnd(dataList,isRefreshData);
        updateIndicator(getRecyclerView(), getAdapter());
    }
    protected TabLayout createTabLayout(View view) {
        TabLayout indicator = (TabLayout) view.findViewById(R.id.indicator);
        return indicator;
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if(getDataManager().getRefreshEnabled()) {
            return inflater.inflate(R.layout._fragment_recycler_view_pager_with_swipe_refresh_layout, container, false);
        }
        else {
            return inflater.inflate(R.layout._fragment_recycler_view_pager, container, false);
        }
    }
}
