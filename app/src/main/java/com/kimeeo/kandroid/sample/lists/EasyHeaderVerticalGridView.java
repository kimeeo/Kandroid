package com.kimeeo.kandroid.sample.lists;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultProjectDataManager;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.verticalHeaderViews.GridView;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
public class EasyHeaderVerticalGridView extends GridView implements DefaultProjectDataManager.IDataManagerDelegate
{
    protected DataManager createDataManager()
    {
        return new DefaultProjectDataManager(getActivity(),this);
    }

    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }

    public View createHeaderView(LayoutInflater inflater)
    {
        View view =inflater.inflate(R.layout._fragment_header_page_view, null);
        new HeaderHelper(view,getActivity()).create();

        return view;
    }
    public View getNormalItemView(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return inflater.inflate(R.layout._sample_column_cell,null);
    }
    public BaseItemHolder getNormalItemHolder(int viewType,View view)
    {
        if(viewType== ViewTypes.VIEW_ITEM1)
            return new VlistItemHolder1(view);
        else
            return new VlistItemHolder2(view);
    }
    public int getNormalItemViewType(int position,Object item)
    {
        if(position<4)
            return ViewTypes.VIEW_ITEM1;
        else
            return ViewTypes.VIEW_ITEM2;
    }











    //END URL
    public String getNextDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data"+pageData.curruntPage+".txt";
    }
    public String getRefreshDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data_m1.txt";
        //return null;
    }
    //Data Parser
    public Class getLoadedDataParsingAwareClass()
    {
        return SampleDataParser.class;
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
            label.setTextColor(Color.parseColor("#ff0000"));

        }
    }



    // Update View Here
    public class VlistItemHolder2 extends BaseItemHolder{

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
            label.setTextColor(Color.parseColor("#000000"));

        }
    }

}
