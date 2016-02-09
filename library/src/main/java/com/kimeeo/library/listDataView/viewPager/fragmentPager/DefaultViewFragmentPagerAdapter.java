package com.kimeeo.library.listDataView.viewPager.fragmentPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
public class DefaultViewFragmentPagerAdapter extends BaseFragmentViewPagerAdapter {

    private final IFragmentProvider fragmentProvider;



    public DefaultViewFragmentPagerAdapter(FragmentManager fragmentManager, DataManager dataManager, ViewPager viewPager,IFragmentProvider fragmentProvider,OnCallService onCallService,OnItemCreated onItemCreated) {
        super(fragmentManager,dataManager,viewPager,onItemCreated);
        setOnCallService(onCallService);
        this.fragmentProvider = fragmentProvider;
    }
    public Fragment getItemFragment(int position,Object navigationObject)
    {
        return fragmentProvider.getItemFragment( position, navigationObject);
    }
    public String getItemTitle(int position,Object navigationObject)
    {
        return fragmentProvider.getItemTitle( position, navigationObject);
    }
}
