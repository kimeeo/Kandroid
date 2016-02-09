package com.kimeeo.library.listDataView.viewPager.fragmentPager;

import android.support.v4.app.Fragment;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
public interface IFragmentProvider {
    Fragment getItemFragment(int position,Object navigationObject);
    String getItemTitle(int position,Object navigationObject);
}
