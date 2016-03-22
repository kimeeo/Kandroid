package com.kimeeo.library.listDataView.viewPager.fragmentPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.kimeeo.library.R;
import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.listDataView.dataManagers.DataChangeWatcher;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.viewPager.jazzyViewPager.JazzyViewPager;
import com.kimeeo.library.model.IFragmentData;

import  android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
abstract public class BaseFragmentViewPagerAdapter extends FragmentStatePagerAdapter implements OnCallService,DataChangeWatcher {
    private OnItemCreated onItemCreated;
    protected void garbageCollectorCall() {
        onItemCreated=null;
        dataManager=null;
        mViewPager=null;
        onCallService=null;
    }

    abstract public Fragment getItemFragment(int position,Object navigationObject);
    abstract public String getItemTitle(int position,Object navigationObject);




    private DataManager dataManager;


    private ViewPager mViewPager;

    public BaseFragmentViewPagerAdapter(FragmentManager fragmentManager, DataManager dataManager, ViewPager viewPager, OnItemCreated onItemCreated) {
        super(fragmentManager);
        this.dataManager=dataManager;
        //must have to be disable
        this.dataManager.setRefreshEnabled(false);
        this.dataManager.setOnCallService(this);
        this.dataManager.setDataChangeWatcher(this);
        this.mViewPager = viewPager;
        this.onItemCreated =onItemCreated;
    }

    public void itemsAdded(int position,List items)
    {
        notifyDataSetChanged();
    }
    public void itemsRemoved(int position,List items)
    {
        notifyDataSetChanged();
    }

    public void removeOnCallService()
    {
        onCallService=null;
    }

    private OnCallService onCallService;
    protected DataManager getDataManager()
    {
        return dataManager;
    }

    public OnCallService getOnCallService()
    {
        return onCallService;
    }
    public void setOnCallService(OnCallService onCallService)
    {
        this.onCallService =onCallService;
    }



    public boolean supportLoader = true;
    public void onCallStart()
    {
        if(supportLoader) {
            getDataManager().add(new ProgressItem());
            notifyDataSetChanged();
        }

        if(onCallService!=null)
            onCallService.onCallStart();
    }



    public void onDataReceived(String url,Object value,Object status)
    {
        List<Object> list = getDataManager();



        if(list.size()!=0 && list.get(list.size() - 1) instanceof ProgressItem && supportLoader)
        {
            getDataManager().remove(getDataManager().size() - 1);
            notifyDataSetChanged();

            if(firstTime) {
                mViewPager.setAdapter(this);
                firstTime=false;
            }
        }
        if(onCallService!=null)
            onCallService.onDataReceived(url, value, status);
    }
    boolean firstTime = true;
    final public void onCallEnd(List<?> dataList,boolean isRefreshPage)
    {
        if(dataList!=null && dataList.size()!=0) {
            notifyDataSetChanged();
        }

        onDataCallIn(dataList,isRefreshPage);

        if(onCallService!=null)
            onCallService.onCallEnd(dataList, isRefreshPage);
    }
    public void onDataCallIn(List<?> dataList,boolean isRefreshPage)
    {

    }
    public void onFirstCallEnd()
    {
        if(onCallService!=null)
            onCallService.onFirstCallEnd();
    }
    public void onLastCallEnd()
    {
        if(onCallService!=null)
            onCallService.onLastCallEnd();
    }
    public void onDataLoadError(String url, Object status)
    {
        if(onCallService!=null)
            onCallService.onDataLoadError(url, status);
    }


    // Returns total number of pages
    @Override
    public int getCount() {
        if(getDataManager()!=null)
            return getDataManager().size();
        return 0;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        Object navigationObject = getDataManager().get(position);
        Fragment fragment = getItemFragment(position,navigationObject);
        if(onItemCreated!=null)
            onItemCreated.onItemCreated(fragment);

        if(mViewPager!=null && mViewPager instanceof JazzyViewPager)
            ((JazzyViewPager)mViewPager).setObjectForPosition(fragment, position);
        return fragment;
    }
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container,position,object);
        Fragment fragment = (Fragment)object;
        if(fragment instanceof BaseFragment)
            ((BaseFragment)fragment).onDestroyItem();
        if(onItemCreated!=null)
            onItemCreated.onDestroyItem(fragment);
    }
    public CharSequence getPageTitle(int position)
    {
        Object navigationObject = getDataManager().get(position);
        return getItemTitle(position,navigationObject);
    }



    public static class ProgressItem implements IFragmentData
    {
        private Object value;

        public Class getView()
        {
            return ProgressbarView.class;
        };
        public String getID()
        {
            return "";
        }
        public String getName()
        {
            return "";
        }
        public  Object getParam()
        {
            return value;
        }
        public void setParam(Object value)
        {
            this.value=value;
        }
        public Object getActionValue()
        {
            return "";
        }
        public String getActionType(){
            return "";
        }
    }

    public static class ProgressbarView extends BaseFragment
    {
        protected void garbageCollectorCall() {

        }
        protected void configViewParam()
        {

        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout._progress_item, container, false);
            return rootView;
        }
    }
    public static interface OnItemCreated
    {
        void onItemCreated(Fragment page);
        void onDestroyItem(Fragment page);
    }
}

