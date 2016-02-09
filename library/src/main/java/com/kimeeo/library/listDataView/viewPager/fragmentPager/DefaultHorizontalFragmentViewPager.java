package com.kimeeo.library.listDataView.viewPager.fragmentPager;

import android.support.v4.app.Fragment;

import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.model.IFragmentData;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
abstract public class DefaultHorizontalFragmentViewPager extends BaseHorizontalFragmentViewPager
{
    public Fragment getItemFragment(int position,Object navigationObject)
    {
        if(navigationObject instanceof IFragmentData)
        {
            BaseFragment activePage = BaseFragment.newInstance((IFragmentData)navigationObject);
            return activePage;
        }
        return
                null;
    }
    @Override
    public String getItemTitle(int position,Object navigationObject)
    {
        if(navigationObject instanceof IFragmentData)
        {
            return ((IFragmentData)navigationObject).getName();
        }
        return super.getItemTitle(position,navigationObject);
    }
}
