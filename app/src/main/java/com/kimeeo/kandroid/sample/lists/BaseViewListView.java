package com.kimeeo.kandroid.sample.lists;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.listViews.BaseItemHolder;
import com.kimeeo.library.listDataView.listViews.BaseListViewAdapter;
import com.kimeeo.library.listDataView.listViews.DefaultListViewAdapter;
import com.kimeeo.library.listDataView.viewHelper.ListViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class BaseViewListView extends BaseFragment implements com.kimeeo.library.listDataView.listViews.IViewProvider {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView;
        DataManager dataManager= createDataManager();
        if(dataManager.getRefreshEnabled())
            rootView= inflater.inflate(com.kimeeo.library.R.layout._fragment_list_view_with_swipe_refresh_layout, container, false);
        else
            rootView= inflater.inflate(com.kimeeo.library.R.layout._fragment_list_view, container, false);

        ListViewHelper helper=new ListViewHelper();
        ListView listView = (ListView) rootView.findViewById(com.kimeeo.library.R.id.listView);
        helper.with(listView);

        if(rootView.findViewById(com.kimeeo.library.R.id.swipeRefreshLayout)!=null) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(com.kimeeo.library.R.id.swipeRefreshLayout);
            helper.swipeRefreshLayout(swipeRefreshLayout);
        }

        View emptyView = rootView.findViewById(com.kimeeo.library.R.id.emptyView);
        if(emptyView!=null)
            helper.emptyView(emptyView);


        if(rootView.findViewById(com.kimeeo.library.R.id.emptyViewImage)!=null) {
            ImageView emptyViewImage = (ImageView) rootView.findViewById(com.kimeeo.library.R.id.emptyViewImage);
            if (emptyViewImage != null)
                helper.emptyImageView(emptyViewImage);
        }

        if(rootView.findViewById(com.kimeeo.library.R.id.emptyViewMessage)!=null) {
            TextView emptyViewMessage = (TextView) rootView.findViewById(com.kimeeo.library.R.id.emptyViewMessage);
            if (emptyViewMessage != null)
                helper.emptyMessageView(emptyViewMessage);
        }



        BaseListViewAdapter adapter=createListViewAdapter(dataManager);
        helper.adapter(adapter);



        helper.dataManager(dataManager);
        helper.setOnItemClick(new ListViewHelper.OnItemClick() {
            @Override
            public void onItemClick(Object baseObject) {
                Toast.makeText(getActivity(), baseObject.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        try
        {
            helper.create();
        }catch (Exception e){}

        return rootView;
    }
    protected void garbageCollectorCall()
    {

    }

    // Data Manager
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
    private SampleModel getSample(String name, String phone) {
        SampleModel o = new SampleModel();
        o.name =name;
        o.details = phone;
        return o;
    }

    protected BaseListViewAdapter createListViewAdapter(DataManager dataManager)
    {
        return new DefaultListViewAdapter(dataManager,null,this);
    }
    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 1;
        public static final int VIEW_ITEM2 = 2;
    }

    public int getListItemViewType(int position,Object item)
    {
        if(position<4)
            return ViewTypes.VIEW_ITEM1;
        else
            return ViewTypes.VIEW_ITEM2;
    }
    public int getTotalViewTypeCount()
    {
        return 2;
    }

    public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return inflater.inflate(R.layout._sample_column_cell,null);
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
