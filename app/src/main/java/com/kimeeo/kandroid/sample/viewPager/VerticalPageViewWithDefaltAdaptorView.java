package com.kimeeo.kandroid.sample.viewPager;

import android.view.LayoutInflater;
import android.view.View;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.lists.SampleDataParser;
import com.kimeeo.kandroid.sample.lists.holder.ViewPagerItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.ViewPagerItemHolder2;
import com.kimeeo.kandroid.sample.projectCore.DefaultProjectDataManager;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.viewPager.VerticalViewPager;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
public class VerticalPageViewWithDefaltAdaptorView extends VerticalViewPager implements DefaultProjectDataManager.IDataManagerDelegate {
    @Override
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
            return new ViewPagerItemHolder1(view);
        else
            return new ViewPagerItemHolder2(view);
    }









    @Override
    protected DataManager createDataManager()
    {
        return new DefaultProjectDataManager(getActivity(),this);
    }
    //END URL
    public String getNextDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data"+pageData.curruntPage+".txt";
    }
    public String getRefreshDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data_m1.txt";
    }
    //Data Parser
    public Class getLoadedDataParsingAwareClass()
    {
        return SampleDataParser.class;
    }
}
