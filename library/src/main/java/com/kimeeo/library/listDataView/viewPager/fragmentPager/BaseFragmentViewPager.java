package com.kimeeo.library.listDataView.viewPager.fragmentPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.viewPager.BaseViewPagerAdapter;
import com.kimeeo.library.listDataView.viewPager.BaseViewPager;
/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
abstract  public class BaseFragmentViewPager extends BaseViewPager implements BaseFragmentViewPagerAdapter.OnItemCreated
{

    protected void garbageCollectorCall() {
        super.garbageCollectorCall();
        if(mFragmentAdapter!=null)
            mFragmentAdapter.garbageCollectorCall();
        mFragmentAdapter=null;
        segmentTabLayout=null;
    }
    public String getPageTitle(int position, Object o)
    {
        return "";
    }

    public void onItemCreated(Fragment page)
    {

    }
    public void onDestroyItem(Fragment page)
    {

    }

    SegmentTabLayout segmentTabLayout;
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

    protected String[] getTabData() {
        if(getDataManager().size()==0) {
            String[] list = new String[1];
            list[0]="";
            return list;
        }
        else {
            String[] list = new String[getDataManager().size()];
            for (int i = 0; i < getDataManager().size(); i++) {
                list[i] = getFragmentAdapter().getPageTitle(i) + "";
            }
            return list;
        }
    }


    final public String getRefreshDataURL(PageData pageData)
    {
        return null;
    }
    final protected BaseViewPagerAdapter createViewPagerAdapter(){return null;}
    final public View getView(int position, Object data){return null;}
    final public BaseItemHolder getItemHolder(View view, int position, Object data){return null;}
    final public BaseViewPagerAdapter getAdapter() {return null;}

    public BaseFragmentViewPagerAdapter getFragmentAdapter() {
        return mFragmentAdapter;
    }
    protected BaseFragmentViewPagerAdapter mFragmentAdapter;

    abstract protected BaseFragmentViewPagerAdapter createViewPagerFragmentAdapter(FragmentManager fragmentManager,DataManager dataManager,ViewPager viewPager);


    @Override
    protected void createAdapter(ViewPager mViewPager) {
        mFragmentAdapter = createViewPagerFragmentAdapter(getChildFragmentManager(),getDataManager(),getViewPager());
        mViewPager.setAdapter(mFragmentAdapter);
    }

}
