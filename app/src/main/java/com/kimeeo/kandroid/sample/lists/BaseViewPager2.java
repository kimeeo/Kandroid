package com.kimeeo.kandroid.sample.lists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.model.SampleModelFrgament;
import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.viewHelper.ViewPagerFragmentHelper;
import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.fragmentPager.BaseFragmentViewPagerAdapter;
import com.kimeeo.library.listDataView.viewPager.fragmentPager.DefaultViewFragmentPagerAdapter;
import com.kimeeo.library.listDataView.viewPager.fragmentPager.IFragmentProvider;
import com.kimeeo.library.listDataView.viewPager.viewPager.IViewProvider;
import com.kimeeo.library.model.IFragmentData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 2/2/16.
 */
public class BaseViewPager2 extends BaseFragment implements IViewProvider, IFragmentProvider
{

    ViewPager viewPager;
    DataManager dataManager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        DataManager dataManager= createDataManager();
        View rootView;
        if(dataManager.getRefreshEnabled())
            rootView= inflater.inflate(com.kimeeo.library.R.layout._fragment_page_view_with_swipe_refresh_layout, container, false);
        else
            rootView= inflater.inflate(com.kimeeo.library.R.layout._fragment_page_view, container, false);

        ViewPagerFragmentHelper viewPagerHelper = new ViewPagerFragmentHelper();
        viewPager=(ViewPager) rootView.findViewById(com.kimeeo.library.R.id.viewPager);
        viewPagerHelper.with(viewPager);
        viewPagerHelper.dataManager(dataManager);

        BaseFragmentViewPagerAdapter adapter=createViewPagerFragmentAdapter(dataManager,viewPager);
        viewPagerHelper.fragmentAdapter(adapter);

        if(rootView.findViewById(com.kimeeo.library.R.id.swipeRefreshLayout)!=null) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(com.kimeeo.library.R.id.swipeRefreshLayout);
            viewPagerHelper.swipeRefreshLayout(swipeRefreshLayout);
        }

        View emptyView = rootView.findViewById(com.kimeeo.library.R.id.emptyView);
        if(emptyView!=null)
            viewPagerHelper.emptyView(emptyView);


        if(rootView.findViewById(com.kimeeo.library.R.id.emptyViewImage)!=null) {
            ImageView emptyViewImage = (ImageView) rootView.findViewById(com.kimeeo.library.R.id.emptyViewImage);
            if (emptyViewImage != null)
                viewPagerHelper.emptyImageView(emptyViewImage);
        }

        if(rootView.findViewById(com.kimeeo.library.R.id.emptyViewMessage)!=null) {
            TextView emptyViewMessage = (TextView) rootView.findViewById(com.kimeeo.library.R.id.emptyViewMessage);
            if (emptyViewMessage != null)
                viewPagerHelper.emptyMessageView(emptyViewMessage);
        }

        View indicator = rootView.findViewById(com.kimeeo.library.R.id.indicator);
        if(indicator!=null)
            viewPagerHelper.indicator(indicator);


        try
        {
            viewPagerHelper.create();
        }catch (Exception e){}



        return rootView;
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
    public void removeView(View view, int position, com.kimeeo.library.listDataView.viewPager.BaseItemHolder itemHolder) {

    }
    protected BaseFragmentViewPagerAdapter createViewPagerFragmentAdapter(DataManager dataManager,ViewPager viewPager)
    {
        FragmentManager fragmentManager= getChildFragmentManager();
        return new DefaultViewFragmentPagerAdapter(fragmentManager,dataManager,viewPager,this,null,null);
    }


    public Fragment getItemFragment(int position,Object navigationObject)
    {
        BaseFragment activePage = BaseFragment.newInstance((IFragmentData)navigationObject);
        return activePage;
    }
    protected DataManager createDataManager()
    {
        ListDataManager listData1= new ListDataManager(getActivity(),listData);
        listData1.setRefreshEnabled(false);
        return listData1;
    }

    IListProvider listData=new IListProvider()
    {
        public List<?> getList(PageData data,Map<String, Object> param)
        {
            if(data.curruntPage==1) {
                List<SampleModelFrgament> list = new ArrayList<>();
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
    private SampleModelFrgament getSample(String name, String phone) {
        SampleModelFrgament o = new SampleModelFrgament();
        o.name =name;
        o.details = phone;
        return o;
    }


    public String getItemTitle(int position, Object o) {
        return position+"";
    }
    @Override
    public View getView(int position, Object data) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout._sample_column_cell,null);
        return view;
    }

    @Override
    public BaseItemHolder getItemHolder(View view, int position, Object data) {
        if(position<4)
            return new VlistItemHolder1(view);
        else
            return new VlistItemHolder2(view);
    }


    // Update View Here
    public class VlistItemHolder1 extends BaseItemHolder
    {

        @Bind(R.id.label)TextView label;
        @Bind(R.id.backgroud)ImageView image;

        public VlistItemHolder1(View itemView)
        {
            super(itemView);
        }

        public void updateItemView(Object item,View view,int position)
        {
            SampleModel listObject = (SampleModel)item;
            label.setText(position + " -> " + listObject.name);
        }
        public void cleanView(View itemView,int position)
        {

        }
    }



    // Update View Here
    public class VlistItemHolder2 extends BaseItemHolder {

        @Bind(R.id.label)TextView label;
        @Bind(R.id.backgroud)ImageView image;

        public VlistItemHolder2(View itemView)
        {
            super(itemView);
        }

        public void updateItemView(Object item,View view,int position)
        {
            SampleModel listObject = (SampleModel)item;
            label.setText(position + " -> " + listObject.name);
            label.setTextColor(view.getContext().getResources().getColor(R.color.actionBarBackgroundStart));
        }
        public void cleanView(View itemView,int position)
        {

        }
    }

}