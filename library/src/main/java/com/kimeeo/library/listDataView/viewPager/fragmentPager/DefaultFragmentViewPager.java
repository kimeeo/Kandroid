package com.kimeeo.library.listDataView.viewPager.fragmentPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.kimeeo.library.listDataView.dataManagers.DataManager;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
abstract public class DefaultFragmentViewPager extends BaseFragmentViewPager implements IFragmentProvider{

    abstract public Fragment getItemFragment(int position,Object navigationObject);


    protected BaseFragmentViewPagerAdapter createViewPagerFragmentAdapter(FragmentManager fragmentManager,DataManager dataManager,ViewPager viewPager)
    {
        return new DefaultViewFragmentPagerAdapter(fragmentManager,dataManager,viewPager,this,this,this);
    }
}
