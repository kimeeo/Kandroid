package com.kimeeo.kandroid.sample.lists;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultProjectDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseProfileRecyclerView;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.recyclerView.viewProfiles.BaseViewProfile;
import com.kimeeo.library.listDataView.recyclerView.viewProfiles.HorizontalList;
import com.kimeeo.library.listDataView.recyclerView.viewProfiles.VerticalGrid;
import com.kimeeo.library.listDataView.recyclerView.viewProfiles.VerticalList;
import com.kimeeo.library.listDataView.recyclerView.viewProfiles.VerticalStaggeredGrid;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 1/12/16.
 */
public class ProfileBasedListView extends BaseProfileRecyclerView implements DefaultProjectDataManager.IDataManagerDelegate {

    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }
    //Data Parser
    public Class getLoadedDataParsingAwareClass()
    {
        return SampleDataParser.class;
    }


    // Data Manager
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
    List<BaseViewProfile> viewProfileList =null;
    public void onViewCreated(View view) {
        if(viewProfileList==null) {
            viewProfileList = new ArrayList<>();
            viewProfileList.add(new MyVerticalListViewProfile("List View",this));
            viewProfileList.add(new MyGridViewProfile("Grid View",this));
            viewProfileList.add(new MyVerticalStaggeredGridViewProfile("Staggered View",this));
            viewProfileList.add(new MyHorizontalListViewProfile("H List View",this));
        }
        applyProfile(viewProfileList.get(0));
        setHasOptionsMenu(true);
        getActivity().supportInvalidateOptionsMenu();
        loadNext();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        for (BaseViewProfile viewProfile:viewProfileList) {
            menu.add(viewProfile.getName());
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        for (BaseViewProfile viewProfile:viewProfileList) {
            if(item.getTitle().equals(viewProfile.getName()))
            {
                applyProfile(viewProfile);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public  class MyVerticalListViewProfile extends VerticalList
    {
        public MyVerticalListViewProfile(String name, BaseProfileRecyclerView host)
        {
            super(name,host);
        }

        public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
        {
            return inflater.inflate(R.layout._sample_column_cell,null);
        }
        public BaseItemHolder getItemHolder(int viewType,View view)
        {
            if(viewType== ViewTypes.VIEW_ITEM1)
                return new VlistItemHolder1(view);
            else
                return new VlistItemHolder2(view);
        }

        public int getListItemViewType(int position,Object item)
        {
            if(position<4)
                return ViewTypes.VIEW_ITEM1;
            else
                return ViewTypes.VIEW_ITEM2;
        }
    }
    public  class MyHorizontalListViewProfile extends HorizontalList
    {
        public MyHorizontalListViewProfile(String name, BaseProfileRecyclerView host)
        {
            super(name,host);
        }

        public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
        {
            return inflater.inflate(R.layout._sample_column_cell,null);
        }
        public BaseItemHolder getItemHolder(int viewType,View view)
        {
            if(viewType== ViewTypes.VIEW_ITEM1)
                return new VlistItemHolder1(view);
            else
                return new VlistItemHolder2(view);
        }

        public int getListItemViewType(int position,Object item)
        {
            if(position<4)
                return ViewTypes.VIEW_ITEM1;
            else
                return ViewTypes.VIEW_ITEM2;
        }
    }




    public class MyGridViewProfile extends VerticalGrid
    {
        public MyGridViewProfile(String name,BaseProfileRecyclerView host)
        {
            super(name,host);

        }


        public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
        {
            return inflater.inflate(R.layout._sample_column_cell,null);
        }
        public BaseItemHolder getItemHolder(int viewType,View view)
        {
            if(viewType== ViewTypes.VIEW_ITEM1)
                return new VlistItemHolder1(view);
            else
                return new VlistItemHolder2(view);
        }

        public int getListItemViewType(int position,Object item)
        {
            if(position<4)
                return ViewTypes.VIEW_ITEM1;
            else
                return ViewTypes.VIEW_ITEM2;
        }
    }

    public class MyVerticalStaggeredGridViewProfile extends VerticalStaggeredGrid
    {
        public MyVerticalStaggeredGridViewProfile(String name,BaseProfileRecyclerView host)
        {
            super(name,host);
        }


        public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
        {
            return inflater.inflate(R.layout._sample_column_cell,null);
        }
        public BaseItemHolder getItemHolder(int viewType,View view)
        {
            if(viewType== ViewTypes.VIEW_ITEM1)
                return new VlistItemHolder1(view);
            else
                return new VlistItemHolder2(view);
        }

        public int getListItemViewType(int position,Object item)
        {
            if(position<4)
                return ViewTypes.VIEW_ITEM1;
            else
                return ViewTypes.VIEW_ITEM2;
        }
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
            AQuery aq = new AQuery(view);
            aq.id(R.id.backgroud).image(listObject.image, true, true, 100, 0);

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
            AQuery aq = new AQuery(view);
            aq.id(R.id.backgroud).image(listObject.image, true, true,100,0);
        }
    }













}
