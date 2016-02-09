package com.kimeeo.kandroid.sample.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.verticalViews.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
public class SimpleListView extends ListView
{
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


    protected BaseRecyclerViewAdapter createListViewAdapter()
    {
        return new DefaultRecyclerIndexableViewAdapter(getDataManager(),this);
    }

    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
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
