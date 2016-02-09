package com.kimeeo.kandroid.sample.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultProjectDataManager;
import com.kimeeo.library.listDataView.listViews.BaseItemHolder;
import com.kimeeo.library.listDataView.listViews.carouselView.DefaultCarouselView;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 1/22/16.
 */
public class CarouselView extends DefaultCarouselView implements DefaultProjectDataManager.IDataManagerDelegate{

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


    public void onItemClick(Object baseObject)
    {
        if(baseObject instanceof SampleModel)
        {
            SampleModel listObject = (SampleModel)baseObject;
            Toast.makeText(getActivity(), listObject.name + " Click", Toast.LENGTH_SHORT).show();
        }
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

        public VlistItemHolder1(View itemView )
        {
            super(itemView);
        }

        public void updateItemView(Object item,View view,int position)
        {
            SampleModel listObject = (SampleModel)item;
            label.setText(position + " -> " + listObject.name);
        }
    }

    // Update View Here
    public class VlistItemHolder2 extends BaseItemHolder {

        @Bind(R.id.label)TextView label;

        public VlistItemHolder2(View itemView)
        {
            super(itemView);
        }

        public void updateItemView(Object item,View view,int position)
        {
            SampleModel listObject = (SampleModel)item;
            label.setText(position +" -> "+listObject.name);
            label.setTextColor(view.getContext().getResources().getColor(R.color.actionBarBackgroundStart));
        }
    }


}
