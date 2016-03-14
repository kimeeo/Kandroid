package com.kimeeo.kandroid.sample.lists;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.lists.holder.ViewPagerItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.ViewPagerItemHolder2;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.viewHelper.ViewPagerHelper;
import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.jazzyViewPager.JazzyViewPager;
import com.kimeeo.library.listDataView.viewPager.viewPager.BaseViewPagerAdapter;
import com.kimeeo.library.listDataView.viewPager.viewPager.DefaultViewPagerAdapter;
import com.kimeeo.library.listDataView.viewPager.viewPager.IViewProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class HeaderHelper implements IViewProvider
{

    ViewPager viewPager;
    DataManager dataManager;
    Activity activity;
    ListDataManager listData1;
    IListProvider listData = new IListProvider() {
        public List<?> getList(PageData data, Map<String, Object> param) {
            if (data.curruntPage == 1) {
                List<SampleModel> list = new ArrayList<>();
                list.add(getSample("B1", "534534"));
                list.add(getSample("B2", "534534"));
                list.add(getSample("B3", "534534"));
                list.add(getSample("B4", "534534"));
                list.add(getSample("B5", "534534"));
                list.add(getSample("B6", "534534"));
                list.add(getSample("B7", "534534"));
                list.add(getSample("B8", "534534"));
                list.add(getSample("B9", "534534"));
                list.add(getSample("B10", "534534"));
                return list;
            }
            return null;
        }
    };
    private View rootView;
    public HeaderHelper(View rootView,Activity activity)
    {
        this.activity=activity;
        this.rootView=rootView;
    }

    public void create()
    {
        ViewPagerHelper viewPagerHelper = new ViewPagerHelper();
        viewPager=(ViewPager) rootView.findViewById(com.kimeeo.library.R.id.viewPager);
        viewPagerHelper.with(viewPager);
        dataManager = createDataManager();
        viewPagerHelper.dataManager(dataManager);
        viewPagerHelper.adapter(createViewPagerAdapter());

        viewPagerHelper.transitionEffect(JazzyViewPager.TransitionEffect.CubeOut);

        if(rootView.findViewById(com.kimeeo.library.R.id.swipeRefreshLayout)!=null) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(com.kimeeo.library.R.id.swipeRefreshLayout);
            viewPagerHelper.swipeRefreshLayout(swipeRefreshLayout);
        }

        View emptyView = rootView.findViewById(com.kimeeo.library.R.id.emptyView);
        if(emptyView!=null)
            viewPagerHelper.emptyView(emptyView);


        View indicator = rootView.findViewById(com.kimeeo.library.R.id.indicator);
        if(indicator!=null)
            viewPagerHelper.indicator(indicator);


        try
        {
            viewPagerHelper.create();
        }catch (Exception e){}
    }

    public ViewPager getViewPager()
    {
        return viewPager;
    }

    protected void garbageCollectorCall()
    {
        viewPager=null;
        dataManager=null;
    }

    public void removeView(View view, int position, BaseItemHolder itemHolder) {

    }

    protected BaseViewPagerAdapter createViewPagerAdapter()
    {
        return new DefaultViewPagerAdapter(dataManager,this,null);
    }

    // Data Manager
    protected DataManager createDataManager()
    {
        ListDataManager listData1= new ListDataManager(activity,listData);
        listData1.setRefreshEnabled(false);
        return listData1;
    }

    private SampleModel getSample(String name, String phone) {
        SampleModel o = new SampleModel();
        o.name =name;
        o.details = phone;
        return o;
    }


    public String getItemTitle(int position, Object o) {
        return position+"";
    }
    @Override
    public View getView(int position, Object data) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout._sample_column_cell,null);
        return view;
    }

    @Override
    public BaseItemHolder getItemHolder(View view, int position, Object data) {
        if(position<4)
            return new ViewPagerItemHolder1(view);
        else
            return new ViewPagerItemHolder2(view);
    }
}
