package com.kimeeo.library.listDataView.viewPager.viewPager;

import android.view.View;

import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.BaseViewPager;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
abstract public class DefaltViewPager extends BaseViewPager implements IViewProvider {

    abstract public View getView(int position, Object data);
    public void removeView(View view, int position, BaseItemHolder itemHolder) {

    }
    abstract public BaseItemHolder getItemHolder(View view, int position, Object data);


    protected BaseViewPagerAdapter createViewPagerAdapter()
    {
        return new DefaultViewPagerAdapter(getDataManager(),this,this);
    }
}
