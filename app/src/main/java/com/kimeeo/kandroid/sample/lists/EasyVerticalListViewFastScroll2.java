package com.kimeeo.kandroid.sample.lists;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultVerticalListView;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import butterknife.Bind;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

/**
 * Created by bhavinpadhiyar on 1/19/16.
 */
public class EasyVerticalListViewFastScroll2 extends DefaultVerticalListView
{
    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout._fragment_recycler_fast_scroll2, container, false);
        return rootView;
    }
    public void onViewCreated(View rootView) {
        RecyclerView recyclerView = getRecyclerView();
        if(recyclerView instanceof FastScrollRecyclerView)
        {

        }

    }

    //Return View Type here
    @Override
    public int getListItemViewType(int position,Object item)
    {
        if(position<4)
            return ViewTypes.VIEW_ITEM1;
        else
            return ViewTypes.VIEW_ITEM2;
    }
    // get View
    @Override
    public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return inflater.inflate(R.layout._sample_column_cell,null);
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






    // get New BaseItemHolder
    @Override
    public BaseItemHolder getItemHolder(int viewType,View view)
    {
        if(viewType== ViewTypes.VIEW_ITEM1)
            return new VlistItemHolder1(view);
        else
            return new VlistItemHolder2(view);
    }
    // Update View Here
    public class VlistItemHolder1 extends BaseItemHolder {

        @Bind(R.id.label)TextView label;
        @Bind(R.id.backgroud)ImageView image;
        //private ViewDataBinding binding;

        public VlistItemHolder1(View itemView)
        {
            super(itemView);
            //binding= DataBindingUtil.bind(itemView);

        }
        /*
        public ViewDataBinding getBinding()
        {
            return binding;
        }
*/
        public void updateItemView(Object item,View view,int position)
        {
            SampleModel listObject = (SampleModel)item;
            label.setText(position + " -> " + listObject.name);
            //AQuery aq = new AQuery(view);
            //aq.id(R.id.backgroud).image(listObject.image, true, true, 100, 0);


            //getBinding().setVariable(BR.myBook, listObject);
            // getBinding().executePendingBindings();

        }
    }



    // Update View Here
    public class VlistItemHolder2 extends BaseItemHolder {

        @Bind(R.id.label)TextView label;
        @Bind(R.id.backgroud)ImageView image;


        //private ViewDataBinding binding;

        public VlistItemHolder2(View itemView)
        {
            super(itemView);

            //binding= DataBindingUtil.bind(itemView);
        }
        /*
        public ViewDataBinding getBinding()
        {
            return binding;
        }*/

        public void updateItemView(Object item,View view,int position)
        {
            SampleModel listObject = (SampleModel)item;

            label.setText(position + " -> " + listObject.name);
            //AQuery aq = new AQuery(view);
            //aq.id(R.id.backgroud).image(listObject.image, true, true, 100, 0);

            //getBinding().setVariable(BR.myBook, listObject);
            //getBinding().executePendingBindings();
        }
    }





}
