package com.kimeeo.kandroid.sample.viewPager;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.lists.SampleDataParser;
import com.kimeeo.kandroid.sample.lists.holder.ViewPagerItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.ViewPagerItemHolder2;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultProjectDataManager;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.flippableStackView.VerticalFlippableStackViewPager;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
public class HorizontalFlipableViewWithDefaltAdaptorView extends VerticalFlippableStackViewPager implements DefaultProjectDataManager.IDataManagerDelegate {
    @Override
    public String getPageTitle(int position, Object o) {
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









    //TAB VIEW STARS HERE
    protected RecyclerTabLayout.Adapter<?> getRecyclerViewTabProvider(ViewPager viewPager) {
        return null;
        //return new TabIndicatorRecyclerViewAdapter(viewPager,getDataManager());
    }






    public class TabIndicatorRecyclerViewAdapter extends com.kimeeo.library.listDataView.viewPager.TabIndicatorRecyclerViewAdapter {

        public TabIndicatorRecyclerViewAdapter(ViewPager viewPager, DataManager dataManager) {
            super(viewPager,dataManager);
        }

        protected ViewHolder getViewHolder(View view, int pos)
        {
            return new MyViewHolder(view);
        }

        protected View getView(ViewGroup parent, int pos)
        {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_tab_view, parent, false);
        }
        public int getItemCount() {
            return getDataManager().size();
        }


        public class MyViewHolder extends ViewHolder {

            @Bind(R.id.textView)
            public TextView  textView;

            @Bind(R.id.progressBar2)
            public ProgressBar progressBar2;


            public MyViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            public void udateItem(int position, Object item) {
                SampleModel data = (SampleModel) item;
                textView.setText(data.name);
            }

            @Override
            public void updatedSelectedItem(Object o) {
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.actionBarBackgroundStart));
            }

            @Override
            public void updatedNormalItem(Object o)
            {
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void configProgressItem() {
                progressBar2.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
            }

            @Override
            public void configNormalItem() {
                progressBar2.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void updateFirst(Object item) {
                textView.setText("F - >"+textView.getText());
            }

            @Override
            public void updateLast(Object item) {
                textView.setText("L - >"+textView.getText());
            }

            @Override
            public void updateMiddel(Object item) {
                textView.setText(textView.getText());
            }
        }
    }
    //TAB VIEW ENDS HERE

}
