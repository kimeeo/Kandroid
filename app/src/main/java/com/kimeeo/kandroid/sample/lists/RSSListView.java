package com.kimeeo.kandroid.sample.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.rssDataManager.RSSDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.verticalViews.ListView;

import org.mcsoxford.rss.RSSItem;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
public class RSSListView extends ListView
{
    // Data Manager
    protected DataManager createDataManager()
    {
        RSSDataManager listData1= new RSSDataManager1(getActivity());
        listData1.setRefreshEnabled(false);
        return listData1;
    }

    public class RSSDataManager1 extends RSSDataManager
    {
        public RSSDataManager1(Context context)
        {
            super(context);
        }
        protected String getNextDataURL(PageData data) {
            if(data.curruntPage==1)
                return "http://feeds.bbci.co.uk/news/world/rss.xml";
            return null;
        }

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
            RSSItem listObject = (RSSItem)item;
            label.setText(position + " -> " + listObject.getTitle());
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
            RSSItem listObject = (RSSItem)item;

            label.setText(position + " -> " + listObject.getTitle());
            //AQuery aq = new AQuery(view);
            //aq.id(R.id.backgroud).image(listObject.image, true, true, 100, 0);

            //getBinding().setVariable(BR.myBook, listObject);
            //getBinding().executePendingBindings();
        }
    }



}
