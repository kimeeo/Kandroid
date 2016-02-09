package com.kimeeo.library.listDataView.viewPager.viewPager;

import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.OnCallService;
import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.jazzyViewPager.JazzyViewPager;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
public class DefaultViewPagerAdapter extends BaseViewPagerAdapter
{

    public String getItemTitle(int position,Object data)
    {
        return viewProvider.getItemTitle(position,data);
    }
    public void garbageCollectorCall() {
        super.garbageCollectorCall();
        viewProvider=null;
    }
    private IViewProvider viewProvider;

    public DefaultViewPagerAdapter(DataManager dataManager,IViewProvider pageView,OnCallService onCallService)
    {
        super(dataManager);
        setOnCallService(onCallService);
        this.viewProvider=pageView;
    }
    protected BaseItemHolder getItemHolder(View view,int position,Object data)
    {
        return viewProvider.getItemHolder(view,position, data);
    }
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        if(viewProvider.getViewPager() instanceof JazzyViewPager)
            ((JazzyViewPager)viewProvider.getViewPager()).setObjectForPosition(obj, position);
        return obj;
    }
    protected View getView(int position,Object data)
    {
        return viewProvider.getView(position, data);
    }
    protected void removeView(View view,int position,BaseItemHolder itemHolder)
    {
        viewProvider.removeView((View) view,position,itemHolder);
    }
}
