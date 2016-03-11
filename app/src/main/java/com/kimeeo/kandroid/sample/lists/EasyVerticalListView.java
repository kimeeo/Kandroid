package com.kimeeo.kandroid.sample.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kimeeo.kandroid.sample.activities.CoordinatorLayoutExample;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder2;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultVerticalListView;
import com.kimeeo.library.actions.Action;
import com.kimeeo.library.actions.Download;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.kandroid.R;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
public class EasyVerticalListView extends DefaultVerticalListView
{
    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }
    Action action;
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        super.onItemClick(parent,view,position,id);

        if(action==null)
            action = new Action(getActivity());

        if(position==0)
        {
            String link="http://kmmc.in/wp-content/uploads/2014/01/lesson2.pdf";
            //String location="/aquery/abc";
            String location=null;
            String title="download Test";
            boolean aTrue=true;
            String success="Done";
            String fail="Failed";

            action.download(link,location,true,success,fail,null);
        }
        else if(position==1)
        {
            action.launchActivity(CoordinatorLayoutExample.class);
        }
        else if(position==2)
        {
            Action action1 =new Action(getActivity());
            action1.openChromeTab("http://www.facebook.com");
        }
    }
    public void onItemClick(Object baseObject)
    {
        super.onItemClick(baseObject);
        Toast.makeText(getActivity(), baseObject.toString(), Toast.LENGTH_SHORT).show();
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
            return new RecyncleItemHolder1(view);
        else
            return new RecyncleItemHolder2(view);
    }




}
