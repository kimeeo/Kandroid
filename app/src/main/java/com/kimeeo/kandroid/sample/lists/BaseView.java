package com.kimeeo.kandroid.sample.lists;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder2;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.DefaultDividerDecoration;
import com.kimeeo.library.listDataView.recyclerView.DefaultRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.IViewProvider;
import com.kimeeo.library.listDataView.viewHelper.RecyclerViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
public class BaseView extends BaseFragment implements IViewProvider {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        DataManager dataManager= createDataManager();

        View rootView;
        if(dataManager.getRefreshEnabled())
            rootView= inflater.inflate(com.kimeeo.library.R.layout._fragment_recycler_with_swipe_refresh_layout, container, false);
        else
            rootView= inflater.inflate(com.kimeeo.library.R.layout._fragment_recycler, container, false);

        RecyclerViewHelper helper=new RecyclerViewHelper();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(com.kimeeo.library.R.id.recyclerView);
        helper.with(recyclerView);

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

        helper.layoutManager(createLayoutManager());
        helper.decoration(getDividerDecoration());


        BaseRecyclerViewAdapter adapter=createListViewAdapter(dataManager);
        helper.adapter(adapter);



        helper.dataManager(dataManager);
        helper.setOnItemClick(new RecyclerViewHelper.OnItemClick() {
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

    protected BaseRecyclerViewAdapter createListViewAdapter(DataManager dataManager)
    {
        return new DefaultRecyclerViewAdapter(dataManager,null,this);
    }

    protected DefaultDividerDecoration getDividerDecoration() {
        return new DefaultDividerDecoration(getActivity());
    }
    protected RecyclerView.LayoutManager createLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        return linearLayoutManager;
    }



    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }
    public int getListItemViewType(int position,Object item)
    {
        if(position<4)
            return ViewTypes.VIEW_ITEM1;
        else
            return ViewTypes.VIEW_ITEM2;
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
            return new RecyncleItemHolder1(view);
        else
            return new RecyncleItemHolder2(view);
    }
}
