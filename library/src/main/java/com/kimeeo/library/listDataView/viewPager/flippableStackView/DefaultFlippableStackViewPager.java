package com.kimeeo.library.listDataView.viewPager.flippableStackView;

import android.view.View;

import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.viewPager.BaseViewPagerAdapter;
import com.kimeeo.library.listDataView.viewPager.viewPager.DefaultViewPagerAdapter;
import com.kimeeo.library.listDataView.viewPager.viewPager.IViewProvider;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
abstract public class DefaultFlippableStackViewPager extends BaseFlippableStackViewPager implements IViewProvider {


    abstract public View getView(int position, Object data);
    public void removeView(View view, int position, BaseItemHolder itemHolder) {

    }
    abstract public BaseItemHolder getItemHolder(View view, int position, Object data);


    protected BaseViewPagerAdapter createViewPagerAdapter()
    {
        return new DefaultViewPagerAdapter(getDataManager(),this,this);
    }
}
