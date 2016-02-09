package com.kimeeo.kandroid.sample.viewPager;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.lists.SampleDataParser;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultProjectDataManager;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.viewPager.BaseItemHolder;
import com.kimeeo.library.listDataView.viewPager.viewPager.HorizontalViewPager;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 1/20/16.
 */
public class HorizontalPageViewWithDefaltAdaptorView extends HorizontalViewPager implements DefaultProjectDataManager.IDataManagerDelegate {
    @Override
    public String getPageTitle(int position, Object o) {
        return position+"";
    }
    /*
    protected JazzyViewPager.TransitionEffect createTransitionEffect() {
        return JazzyViewPager.TransitionEffect.Stack;
    }
    */
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
    public class VlistItemHolder1 extends BaseItemHolder {

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
        protected  ViewHolder getViewHolder(View view)
        {
            return new MyViewHolder(view);
        }
        protected View getView(ViewGroup parent)
        {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_tab_view, parent, false);
        }
        public int getItemCount() {
            return getDataManager().size();
        }


        public class MyViewHolder extends com.kimeeo.library.listDataView.viewPager.TabIndicatorRecyclerViewAdapter.ViewHolder {

            @Bind(R.id.textView)
            public TextView  textView;

            @Bind(R.id.progressBar2)
            public ProgressBar progressBar2;


            public MyViewHolder(View itemView) {
                super(itemView);
            }
            public void updatedSelectedItem(Object o) {
                SampleModel data= (SampleModel) o;
                textView.setText(data.name);
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.actionBarBackgroundStart));
            }
            public void updatedNormalItem(Object o)
            {
                SampleModel data= (SampleModel) o;
                textView.setText(data.name);
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorAccent));
            }
            public void hide() {
                progressBar2.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
            }

            public void show() {
                progressBar2.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }

            public void updateFirst(Object item) {
                textView.setText("F - >"+textView.getText());
            }

            public void updateLast(Object item) {
                textView.setText("L - >"+textView.getText());
            }

            public void updateMiddel(Object item) {
                textView.setText(textView.getText());
            }
        }
    }
    //TAB VIEW ENDS HERE

}
